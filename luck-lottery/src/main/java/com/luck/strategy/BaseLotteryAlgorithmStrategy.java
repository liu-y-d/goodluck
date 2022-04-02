package com.luck.strategy;

import com.luck.vo.PrizeProbabilityInfo;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 抽奖算法基类
 *
 * @author liuyd
 * @date 2022/4/1 11:22
 */
public abstract class BaseLotteryAlgorithmStrategy implements LotteryAlgorithmStrategy {
    /**
     * 数组初始化长度
     */
    private final int RATE_TUPLE_LENGTH = 128;

    /**
     * 存放概率与奖品对应的散列结果，strategyId -> rateTuple
     */
    protected Map<Long, Long[]> rateTupleMap = new ConcurrentHashMap<>();

    /**
     * 奖品区间概率值，strategyId -> [awardId->begin、awardId->end]
     */
    protected Map<Long, List<? extends PrizeProbabilityInfo>> prizeProbabilityInfo = new ConcurrentHashMap<>();

    @Override
    public void initPrizeList(Long activityId, List<? extends PrizeProbabilityInfo> awardRateInfoList) {
        // 保存奖品概率信息
        prizeProbabilityInfo.put(activityId, awardRateInfoList);
    }

    @Override
    public void initRateTuple(Long activityId, List<? extends PrizeProbabilityInfo> awardRateInfoList) {


        Long[] rateTuple = rateTupleMap.computeIfAbsent(activityId, k -> new Long[RATE_TUPLE_LENGTH]);

        int cursorVal = 0;
        for (PrizeProbabilityInfo prizeProbabilityInfo : awardRateInfoList) {
            int rateVal = prizeProbabilityInfo.getPrizeProbability().multiply(new BigDecimal(100)).intValue();
            for (int i = cursorVal + 1; i <= (rateVal + cursorVal); i++) {
                rateTuple[i] = prizeProbabilityInfo.getPrizeId();
            }
            cursorVal += rateVal;
        }
    }

    @Override
    public boolean isExistRateTuple(Long activityId) {
        return rateTupleMap.containsKey(activityId);
    }



    /**
     * 生成百位随机抽奖码
     *
     * @return 随机值
     */
    protected int generateSecureRandomIntCode(int bound) {
        return new SecureRandom().nextInt(bound) + 1;
    }
}
