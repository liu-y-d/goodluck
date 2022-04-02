package com.luck.strategy;

/**
 * 抽奖策略上下文
 *
 * @author liuyd
 * @date 2022/3/31 15:29
 */
public class LotteryStrategyContext {
    private LotteryAlgorithmStrategy lotteryStrategy;

    public LotteryAlgorithmStrategy getLotteryStrategy() {
        return lotteryStrategy;
    }

    public void setLotteryStrategy(String strategyType) {
        this.lotteryStrategy = LotteryStrategyFactory.getLotteryStrategy(strategyType);
    }
}
