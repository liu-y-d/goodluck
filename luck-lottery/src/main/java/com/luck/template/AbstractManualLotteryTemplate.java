package com.luck.template;

import com.luck.api.R;
import com.luck.constant.Constant;
import com.luck.feign.RenrenFeignClient;
import com.luck.strategy.LotteryAlgorithmStrategy;
import com.luck.strategy.LotteryStrategyFactory;
import com.luck.vo.ActivityConfigDetailVo;
import com.luck.vo.LotteryResult;
import com.luck.vo.LotteryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 手动抽奖模板类
 * @author Liuyunda
 * @date 2022/6/8 23:24
 * @email man021436@163.com
 */
@Slf4j
public abstract class AbstractManualLotteryTemplate implements IDrawExec{
    @Autowired
    protected RenrenFeignClient client;
    @Override
    public LotteryResult doDrawExec(LotteryVo lotteryRequest) {
        // 获取活动配置
        R<ActivityConfigDetailVo> activityConfig = client.getConfig(lotteryRequest.getActivityId());
        ActivityConfigDetailVo data = activityConfig.getData();
        if (data.getActivityStatus() == 1) {
            throw new RuntimeException("活动状态以改变，activityId:"+lotteryRequest.getActivityId()+",activityStatus:"+data.getActivityStatus());
        }

        checkController(data,lotteryRequest.getUserId());
        checkActivityConfig(data);
        this.drawAlgorithm(lotteryRequest.getActivityId(), LotteryStrategyFactory.getLotteryStrategy(Constant.LotteryStrategyEnum.valueOf(data.getStrategyType()).getValue()),data,lotteryRequest);
        return null;
    }

    /**
     * 检验控制者是否满足开奖条件
     */
    protected abstract void checkController(ActivityConfigDetailVo data, Long userId);

    /**
     * 检验活动配置
     */
    protected abstract void checkActivityConfig(ActivityConfigDetailVo data);

    protected abstract void drawAlgorithm(Long activityId, LotteryAlgorithmStrategy lotteryStrategy, ActivityConfigDetailVo activityConfig,LotteryVo lotteryRequest);

}
