package com.luck.template;

import com.alibaba.fastjson.JSON;
import com.luck.strategy.LotteryAlgorithmStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 执行抽奖实现类
 *
 * @author liuyd
 * @date 2022/4/1 15:58
 */
@Slf4j
@Service
public class DrawExecImpl extends AbstractLotteryTemplate {
    @Override
    protected List<Long> queryExcludeAwardIds(Long activityId) {
        List<Long> prizeList = this.client.nostock(activityId).getData();
        log.info("执行抽奖策略 activityId：{}，无库存排除奖品列表ID集合 prize：{}", activityId, JSON.toJSONString(prizeList));
        return prizeList;
    }

    @Override
    protected Long drawAlgorithm(Long activityId, LotteryAlgorithmStrategy lotteryStrategy, List<Long> excludeAwardIds, Long defaultPrizeId) {
        // 执行抽奖
        Long prizeId = lotteryStrategy.randomDraw(activityId, excludeAwardIds,defaultPrizeId);

        // 判断抽奖结果
        if (prizeId == null) {
            return null;
        }

        /*
         * 扣减库存，暂时采用数据库行级锁的方式进行扣减库存，后续优化为 Redis 分布式锁扣减 decr/incr
         * 注意：通常数据库直接锁行记录的方式并不能支撑较大体量的并发。
         * 但此方式需要了解，因为在分库分表下的正常数据流量下的个人数据记录中，是可以使用行级锁的，因为他只影响到自己的记录，不会影响到其他人。
         */
        boolean isSuccess = (Boolean)this.client.deductStock(activityId, prizeId).getData();

        // 返回结果，库存扣减成功返回奖品ID，否则返回NULL（在实际的业务场景中，如果中奖奖品库存为空，则会发送兜底奖品，比如各类劵）
        return isSuccess ? prizeId : defaultPrizeId;
    }
}
