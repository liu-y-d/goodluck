package com.luck.template;

import com.luck.api.R;
import com.luck.constant.Constant;
import com.luck.feign.RenrenFeignClient;
import com.luck.rabbitmq.MQSender;
import com.luck.strategy.LotteryAlgorithmStrategy;
import com.luck.strategy.LotteryStrategyFactory;
import com.luck.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 实时抽奖模版类
 *
 * @author liuyd
 * @date 2022/4/1 14:12
 */
@Slf4j
public abstract class AbstractRealTimeLotteryTemplate implements IDrawExec {
    @Autowired
    protected RenrenFeignClient client;

    @Autowired
    protected MQSender mqSender;
    @Override
    public LotteryResult doDrawExec(LotteryVo lotteryRequest) {
        // 1、获取抽奖策略
        // 获取活动配置
        R<ActivityConfigDetailVo> activityConfig = client.getConfig(lotteryRequest.getActivityId());
        ActivityConfigDetailVo data = activityConfig.getData();
        if (data.getActivityStatus() != 0) {
            throw new RuntimeException("活动状态以改变，activityId:"+lotteryRequest.getActivityId()+",activityStatus:"+data.getActivityStatus());
        }
        // 2、校验抽奖策略是否已经初始化到内存
        this.checkAndInitRateData(lotteryRequest.getActivityId(), data.getStrategyType(), data.getPrizeList());

        // 3、获取不在抽奖范围内的列表，包括：奖品库存为空、风控策略、临时调整等
        List<Long> excludeAwardIds = this.queryExcludeAwardIds(lotteryRequest.getActivityId());

        // 4、执行抽奖算法
        // todo 连抽 和 保底算法
        int batchGetNumber;
        if (data.getBatchGetNumber() == 0) {
            batchGetNumber = 1;
        }else {
            if (lotteryRequest.getGetTimes() <= data.getBatchGetNumber()) {
                batchGetNumber = lotteryRequest.getGetTimes();
            }else {
                batchGetNumber = data.getBatchGetNumber();
            }
        }
        // 获取活动参与次数
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("activityId",data.getActivityId());
        Integer joinTimes = client.joinActivityDetail(params).getData().size();

        List<Long> prizeIds = new ArrayList<>(batchGetNumber);
        for (int i = 1; i <= batchGetNumber; i++) {
            Long awardId = this.drawAlgorithm(lotteryRequest.getActivityId(), LotteryStrategyFactory.getLotteryStrategy(Constant.LotteryStrategyEnum.valueOf(data.getStrategyType()).getValue()),
                    excludeAwardIds, data, joinTimes+i);
            prizeIds.add(awardId);
        }
        asyncProcess(prizeIds,lotteryRequest.getActivityId(),lotteryRequest.getTimestamp());
        // 5、包装中奖结果
        return buildDrawResult(lotteryRequest.getUserId(), lotteryRequest.getActivityId(), prizeIds);
    }

    /**
     * 获取不在抽奖范围内的列表，包括：奖品库存为空、风控策略、临时调整等，这类数据是含有业务逻辑的，所以需要由具体的实现方决定
     *
     * @param activityId 活动id
     * @return 排除的奖品ID集合
     */
    protected abstract List<Long> queryExcludeAwardIds(Long activityId);

    /**
     * 执行抽奖算法
     *
     * @param activityId      活动id
     * @param lotteryStrategy   抽奖算法模型
     * @param excludeAwardIds 排除的抽奖id集合
     * @param activityConfig 活动配置
     * @param joinTimes 参与次数
     * @return 中奖奖品ID
     */
    protected abstract Long drawAlgorithm(Long activityId, LotteryAlgorithmStrategy lotteryStrategy,
                                            List<Long> excludeAwardIds, ActivityConfigDetailVo activityConfig , Integer joinTimes);



    /**
     * 校验抽奖策略是否已经初始化到内存
     *
     * @param activityId         活动id
     * @param strategyMode       抽奖策略模式
     * @param probabilityInfos   配置详情
     */
    private void checkAndInitRateData(Long activityId, Integer strategyMode, List<? extends PrizeProbabilityInfo> probabilityInfos) {


        LotteryAlgorithmStrategy lotteryStrategy = LotteryStrategyFactory.getLotteryStrategy(Constant.LotteryStrategyEnum.valueOf(strategyMode).getValue());
        lotteryStrategy.initPrizeList(activityId, probabilityInfos);
        // 非简单概率，不必存入缓存
        if (Constant.LotteryStrategyEnum.LOTTERY_STRATEGY_DYNAMIC.getCode() == strategyMode) {
            return;
        }
        // 已初始化过的数据，不必重复初始化
        if (lotteryStrategy.isExistRateTuple(activityId)) {
            return;
        }

        // 解析并初始化中奖概率数据到散列表
        lotteryStrategy.initRateTuple(activityId, probabilityInfos);
    }

    /**
     * 包装抽奖结果
     *
     * @param uId        用户id
     * @param activityId 活动id
     * @param prizeIds   奖品id
     * @return 中奖结果
     */
    private LotteryResult buildDrawResult(Long uId, Long activityId, List<Long> prizeIds) {
        if (CollectionUtils.isEmpty(prizeIds)) {
            log.info("执行策略抽奖完成，奖品个数：{} 用户：{} 活动id：{}",prizeIds.size(), uId, activityId);
            return new LotteryResult(uId,"", activityId, Constant.DrawState.FAIL.getCode(),null);
        }
        List<PrizeVo> prizes = new ArrayList<>(prizeIds.size());
        R<ActivityConfigDetailVo> config = client.getConfig(activityId);
        ActivityConfigDetailVo data = config.getData();
        List<ActivityPrizeVo> prizeList = data.getPrizeList();
        prizeIds.forEach(prizeId -> {
            ActivityPrizeVo activityPrizeVo = prizeList.stream().filter(p -> p.getPrizeId().equals(prizeId)).findFirst().get();
            prizes.add(new PrizeVo(prizeId, activityPrizeVo.getPrizeName(), activityPrizeVo.getPrizeNumber()));
        });

        log.info("执行策略抽奖完成，用户：{} 活动id：{} 奖品列表：{}", uId, activityId, prizes.toString());

        return new LotteryResult(uId,"", activityId, Constant.DrawState.SUCCESS.getCode(), prizes);
    }

    /**
     * 异步处理
     *
     * @param prizeIds
     * @param activityId
     * @param timestamp
     */
    protected abstract void asyncProcess(List<Long> prizeIds, Long activityId, String timestamp);
}
