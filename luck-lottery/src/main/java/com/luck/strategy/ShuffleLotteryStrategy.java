package com.luck.strategy;

import com.luck.algorithm.ShuffleAlgorithm;
import com.luck.constant.Constant;
import com.luck.vo.ActivityConfigDetailVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 洗牌算法抽奖策略
 * @author Liuyunda
 * @date 2022/6/8 23:58
 * @email man021436@163.com
 */
@Component
public class ShuffleLotteryStrategy extends BaseLotteryShuffleAlgorithmStrategy implements InitializingBean {
    @Override
    public List<Long> randomDraw(Long activityId, ActivityConfigDetailVo activityConfig) {
        if (!CollectionUtils.isEmpty(super.activityFixedCustomers.get(activityId))) {
            return ShuffleAlgorithm.FYShuffle(super.activityFixedCustomers.get(activityId));
        }
        return null;
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        LotteryStrategyFactory.register(Constant.LOTTERY_STRATEGY_SHUFFLE,this);
    }


}
