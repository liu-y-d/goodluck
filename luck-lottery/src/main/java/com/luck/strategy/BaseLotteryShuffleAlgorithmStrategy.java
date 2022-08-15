package com.luck.strategy;

import com.luck.vo.ActivityConfigDetailVo;
import com.luck.vo.PrizeProbabilityInfo;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 抽奖算法基类
 *
 * @author liuyd
 * @date 2022/4/1 11:22
 */
public abstract class BaseLotteryShuffleAlgorithmStrategy implements LotteryAlgorithmStrategy {
    /**
     * 活动固定的人员列表
     */
    protected Map<Long, List<Long>> activityFixedCustomers = new ConcurrentHashMap<>();



    @Override
    public void initCustomerList(Long activityId, ActivityConfigDetailVo activityConfig) {
        Integer participationLimit = activityConfig.getParticipationLimit();
        if (participationLimit == 1 && StringUtils.isNotBlank(activityConfig.getCustomers())) {
            activityFixedCustomers.put(activityId, Arrays.stream(activityConfig.getCustomers().split(",")).map(Long::parseLong).collect(Collectors.toList()));
        }

    }

    @Override
    public void initRateTuple(Long activityId, List<? extends PrizeProbabilityInfo> awardRateInfoList) {
    }

    @Override
    public void initPrizeList(Long activityId, List<? extends PrizeProbabilityInfo> awardRateInfoList) {
    }

    @Override
    public boolean isExistRateTuple(Long activityId) {
        return false;
    }
    @Override
    public Long randomDraw(Long activityId, List<Long> excludeAwardIds, ActivityConfigDetailVo activityConfig, Integer joinTimes) {
        return null;
    }
}
