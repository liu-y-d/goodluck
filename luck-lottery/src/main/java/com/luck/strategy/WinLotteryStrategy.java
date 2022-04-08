package com.luck.strategy;

import com.luck.constant.Constant;
import com.luck.vo.ActivityConfigDetailVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 保底模式
 *
 * @author liuyd
 * @date 2022/3/31 15:26
 */
@Component
public class WinLotteryStrategy  extends BaseLotteryAlgorithmStrategy implements InitializingBean {
    @Override
    public void afterPropertiesSet(){
        LotteryStrategyFactory.register(Constant.LOTTERY_STRATEGY_WIN,this);
    }

    @Override
    public Long randomDraw(Long activityId, List<Long> excludeAwardIds, ActivityConfigDetailVo activityConfig, Integer joinTimes) {
        // 获取策略对应的元组
        Long[] rateTuple = super.rateTupleMap.get(activityId);
        assert rateTuple != null;

        if (joinTimes % activityConfig.getWinPrizeNumber() == 0){
            return rateTuple[1];
        }
        // 随机索引
        int idx = this.generateSecureRandomIntCode(100);

        // 返回结果
        Long prizeId = rateTuple[idx];
        if (excludeAwardIds.contains(prizeId)) {
            return activityConfig.getDefaultPrizeId();
        }

        return prizeId;
    }
}
