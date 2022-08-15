package com.luck.template;

import com.alibaba.fastjson.JSON;
import com.luck.strategy.LotteryAlgorithmStrategy;
import com.luck.utils.AuthUtil;
import com.luck.vo.ActivityConfigDetailVo;
import com.luck.vo.LotteryStockMessage;
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
public class DrawExecImpl extends AbstractRealTimeLotteryTemplate {
    @Override
    protected List<Long> queryExcludeAwardIds(Long activityId) {
        List<Long> prizeList = this.client.nostock(activityId).getData();
        log.info("执行抽奖策略 activityId：{}，无库存排除奖品列表ID集合 prize：{}", activityId, JSON.toJSONString(prizeList));
        return prizeList;
    }

    @Override
    protected Long drawAlgorithm(Long activityId, LotteryAlgorithmStrategy lotteryStrategy, List<Long> excludeAwardIds, ActivityConfigDetailVo activityConfig, Integer joinTimes) {
        // 执行抽奖
        Long prizeId = lotteryStrategy.randomDraw(activityId, excludeAwardIds,activityConfig, joinTimes);

        // 判断抽奖结果
        if (prizeId == null) {
            return activityConfig.getDefaultPrizeId() == null? -1:activityConfig.getDefaultPrizeId();
        }

        /*
         * 扣减库存，暂时采用数据库行级锁的方式进行扣减库存，后续优化为 Redis 分布式锁扣减 decr/incr
         * 注意：通常数据库直接锁行记录的方式并不能支撑较大体量的并发。
         * 但此方式需要了解，因为在分库分表下的正常数据流量下的个人数据记录中，是可以使用行级锁的，因为他只影响到自己的记录，不会影响到其他人。
         */
        long flag = ((Integer) this.client.deductStock(activityId, prizeId).getData()).longValue();
        if (flag == -2) {
            // 库存不足扣减失败
            return activityConfig.getDefaultPrizeId();
        }else if(flag == -1) {
            // 当前商品不限库存
            return prizeId;
        }else {
            return prizeId;
        }
    }

    @Override
    protected void asyncProcess(List<Long> prizeIds, Long activityId, String timestamp) {
        // 4.异步减库存 加数据库
        LotteryStockMessage lotteryStockMessage = new LotteryStockMessage(AuthUtil.getUser().getCId(), activityId, prizeIds,timestamp);
        mqSender.sendStock(JSON.toJSONString(lotteryStockMessage));
    }
}
