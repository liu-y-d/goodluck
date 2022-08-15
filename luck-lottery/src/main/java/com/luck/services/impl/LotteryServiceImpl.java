package com.luck.services.impl;

import com.luck.api.R;
import com.luck.constant.Constant;
import com.luck.entity.CustomerActivityDetailEntity;
import com.luck.entity.CustomerEntity;
import com.luck.entity.CustomerIntegralEntity;
import com.luck.feign.RenrenFeignClient;
import com.luck.services.LotteryService;
import com.luck.template.DrawExecImpl;
import com.luck.template.DrawExecShuffleImpl;
import com.luck.template.IDrawExec;
import com.luck.utils.AuthUtil;
import com.luck.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 抽奖接口实现类
 *
 * @author liuyd
 * @date 2022/3/29 15:58
 */
@Service
@Slf4j
public class LotteryServiceImpl implements LotteryService {

    @Autowired
    private RenrenFeignClient renrenFeignClient;

    @Autowired
    private DrawExecImpl iDrawExec;
    @Autowired
    private DrawExecShuffleImpl iDrawExecShuffle;
    @Override
    public R<LotteryResult> lottery(LotteryVo lotteryVo) {
        // 获取当前登录对象
        LuckUser user = AuthUtil.getUser();
        lotteryVo.setUserId(user.getCId());
        // 验证帐号积分
        R<CustomerIntegralEntity> info = renrenFeignClient.info(user.getCId());
        // 进行抽奖
        LotteryResult lotteryResult = iDrawExec.doDrawExec(lotteryVo);
        return R.success("success");
    }

    @Override
    public R<LotteryResult> lotteryShuffle(LotteryVo lotteryVo) {
        // 获取当前登录对象
        LuckUser user = AuthUtil.getUser();
        lotteryVo.setUserId(user.getCId());
        // 验证帐号积分
        // R<CustomerIntegralEntity> info = renrenFeignClient.info(user.getCId());
        // 进行抽奖
        LotteryResult lotteryResult = iDrawExecShuffle.doDrawExec(lotteryVo);
        return R.success("success");
    }

    @Override
    public R<LotteryResult> getResult(LotteryVo lotteryVo) {
        HashMap<String, Object> params = new HashMap<>(1);
        Long activityId = lotteryVo.getActivityId();
        params.put("activityId", activityId);
        params.put("joinTime", lotteryVo.getTimestamp());
        R<List<CustomerActivityDetailEntity>> listR = renrenFeignClient.joinActivityDetail(params);
        List<CustomerActivityDetailEntity> joinDetails = listR.getData();
        if (CollectionUtils.isEmpty(joinDetails)) {
            throw new RuntimeException("抽奖结果暂未查询到，请等待！");
        }
        List<Long> prizeIds;
        if (lotteryVo.getGetTimes() == null || lotteryVo.getGetTimes() == -1) {
            prizeIds =joinDetails.stream().sorted(Comparator.comparing(CustomerActivityDetailEntity::getJoinTime).reversed()).map(CustomerActivityDetailEntity::getPrizeId).collect(Collectors.toList());
        }else {
            prizeIds = joinDetails.stream().sorted(Comparator.comparing(CustomerActivityDetailEntity::getJoinTime).reversed()).limit(lotteryVo.getGetTimes()).map(CustomerActivityDetailEntity::getPrizeId).collect(Collectors.toList());
        }
        Long uId = AuthUtil.getUser().getCId();
        if (CollectionUtils.isEmpty(prizeIds)) {
            log.info("执行策略抽奖完成，奖品个数：{} 用户：{} 活动id：{}",prizeIds.size(), uId, activityId);
            return R.data(new LotteryResult(uId,"", activityId, Constant.DrawState.FAIL.getCode(),null));
        }
        List<PrizeVo> prizes = new ArrayList<>(prizeIds.size());
        R<ActivityConfigDetailVo> config = renrenFeignClient.getConfig(activityId);
        ActivityConfigDetailVo data = config.getData();
        List<ActivityPrizeVo> prizeList = data.getPrizeList();
        prizeIds.forEach(prizeId -> {
            ActivityPrizeVo activityPrizeVo = prizeList.stream().filter(p -> p.getPrizeId().equals(prizeId)).findFirst().get();
            prizes.add(new PrizeVo(prizeId, activityPrizeVo.getPrizeName(), activityPrizeVo.getPrizeNumber()));
        });

        log.info("执行策略抽奖完成，用户：{} 活动id：{} 奖品列表：{}", uId, activityId, prizes.toString());

        return R.data(new LotteryResult(uId,"", activityId, Constant.DrawState.SUCCESS.getCode(), prizes));
    }

    @Override
    public R<List<LotteryResult>> lotteryShuffleGetResult(LotteryVo lotteryVo) {
        HashMap<String, Object> params = new HashMap<>(1);
        Long activityId = lotteryVo.getActivityId();
        params.put("activityId", activityId);
        params.put("joinTime", lotteryVo.getTimestamp());
        R<List<CustomerActivityDetailEntity>> listR = renrenFeignClient.joinActivityDetail(params);
        List<CustomerActivityDetailEntity> joinDetails = listR.getData();
        if (CollectionUtils.isEmpty(joinDetails)) {
            throw new RuntimeException("抽奖结果暂未查询到，请等待！");
        }
        List<LotteryResult> results = new ArrayList<>();
        R<ActivityConfigDetailVo> config = renrenFeignClient.getConfig(activityId);
        ActivityConfigDetailVo data = config.getData();
        List<ActivityPrizeVo> prizeList = data.getPrizeList();
        joinDetails.forEach(joinDetail->{
            Long prizeId = joinDetail.getPrizeId();
            ActivityPrizeVo activityPrizeVo = prizeList.stream().filter(p -> p.getPrizeId().equals(prizeId)).findFirst().get();
            List<PrizeVo> prizes = new ArrayList<>();
            prizes.add(new PrizeVo(prizeId, activityPrizeVo.getPrizeName(), activityPrizeVo.getPrizeNumber()));
            R<CustomerEntity> customerEntityR = renrenFeignClient.customerInfo(joinDetail.getCId());
            LotteryResult lotteryResult = new LotteryResult(joinDetail.getCId(),customerEntityR.getData().getCName(),activityId,Constant.DrawState.SUCCESS.getCode(),prizes);
            results.add(lotteryResult);
            log.info("执行策略抽奖完成，用户：{} 活动id：{} 奖品列表：{}", joinDetail.getCId(), activityId, prizes.toString());
        });
        return R.data(results);
    }
}
