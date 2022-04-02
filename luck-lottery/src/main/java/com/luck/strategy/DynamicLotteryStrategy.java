package com.luck.strategy;

import com.luck.constant.Constant;
import com.luck.vo.PrizeProbabilityInfo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 动态模型
 * @author liuyd
 * @date 2022/3/31 15:28
 */
@Component
public class DynamicLotteryStrategy  extends BaseLotteryAlgorithmStrategy implements InitializingBean{


    @Override
    public void afterPropertiesSet() throws Exception {
        LotteryStrategyFactory.register(Constant.LOTTERY_STRATEGY_DYNAMIC,this);
    }

    @Override
    public Long randomDraw(Long activityId, List<Long> excludeAwardIds, Long defaultPrizeId) {

        BigDecimal differenceDenominator = BigDecimal.ZERO;

        // 排除掉不在抽奖范围的奖品ID集合
        List<PrizeProbabilityInfo> differenceAwardRateList = new ArrayList<>();
        List<? extends PrizeProbabilityInfo> awardRateIntervalValList = prizeProbabilityInfo.get(activityId);
        for (PrizeProbabilityInfo probabilityInfo : awardRateIntervalValList) {
            Long prizeId = probabilityInfo.getPrizeId();
            if (excludeAwardIds.contains(prizeId)) {
                continue;
            }
            differenceAwardRateList.add(probabilityInfo);
            differenceDenominator = differenceDenominator.add(probabilityInfo.getPrizeProbability());
        }


        // 前置判断：奖品列表为0，返回NULL
        if (differenceAwardRateList.size() == 0) {
            return null;
        }

        // 前置判断：奖品列表为1，直接返回
        if (differenceAwardRateList.size() == 1) {
            return differenceAwardRateList.get(0).getPrizeId();
        }

        // 获取随机概率值
        int randomVal = this.generateSecureRandomIntCode(100);

        // 循环获取奖品
        Long awardId = null;
        int cursorVal = 0;
        for (PrizeProbabilityInfo awardRateInfo : differenceAwardRateList) {
            int rateVal = awardRateInfo.getPrizeProbability().divide(differenceDenominator, 2, BigDecimal.ROUND_UP).multiply(new BigDecimal(100)).intValue();
            if (randomVal <= (cursorVal + rateVal)) {
                awardId = awardRateInfo.getPrizeId();
                break;
            }
            cursorVal += rateVal;
        }

        return awardId;
    }
}