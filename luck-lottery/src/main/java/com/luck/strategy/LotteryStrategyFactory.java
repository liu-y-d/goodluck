package com.luck.strategy;

import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 抽奖策略工厂
 *
 * @author liuyd
 * @date 2022/3/31 15:17
 */
public class LotteryStrategyFactory {
    private static Map<String, LotteryAlgorithmStrategy> STRATEGIES = new ConcurrentHashMap<>(16);

    public static LotteryAlgorithmStrategy getLotteryStrategy(String strategyType) {
        return STRATEGIES.get(strategyType);
    }

    public static void register(String strategyType, LotteryAlgorithmStrategy lotteryStrategy) {
        Assert.notNull(strategyType, "strategyType can't be null");
        STRATEGIES.put(strategyType, lotteryStrategy);
    }
}
