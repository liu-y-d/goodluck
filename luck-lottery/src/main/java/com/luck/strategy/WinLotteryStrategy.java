package com.luck.strategy;

import com.luck.constant.Constant;
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
    public Long randomDraw(Long activityId, List<Long> excludeAwardIds, Long defaultPrizeId) {
        return null;
    }
}
