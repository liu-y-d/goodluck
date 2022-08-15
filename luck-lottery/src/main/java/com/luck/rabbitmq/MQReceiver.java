package com.luck.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.luck.constant.Constant;
import com.luck.feign.RenrenFeignClient;
import com.luck.vo.LotteryStockMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author Liuyunda
 * @date 2022/5/2 12:47
 * @email man021436@163.com
 */
@Service
@Slf4j
public class MQReceiver {
    @Autowired
    private RenrenFeignClient renrenFeignClient;
    /**
     * 减库存，生成中奖记录
     * @param msg
     */
    @RabbitListener(queues = Constant.QUEUE)
    public void receive(@Payload String msg) {
        log.info("接收消息："+msg);
        LotteryStockMessage lotteryStockMessage = JSON.parseObject(msg, LotteryStockMessage.class);
        List<Long> prizeIds = lotteryStockMessage.getPrizeIds();
        Long activityId = lotteryStockMessage.getActivityId();
        Long cId = lotteryStockMessage.getCId();
        prizeIds.forEach(prizeId-> {
            renrenFeignClient.updateStock(activityId,prizeId);
        });
        HashMap<String, Object> params = new HashMap<>(4);
        params.put("activityId",activityId.toString());
        params.put("prizeIds", StringUtils.join(prizeIds.toArray(), ","));
        params.put("joinTime", lotteryStockMessage.getJoinTime());
        params.put("cId", lotteryStockMessage.getCId());
        // 4、记录抽奖信息
        renrenFeignClient.saveJoinDetail(params);
    }
}
