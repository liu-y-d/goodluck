package com.luck.rabbitmq;

import com.luck.constant.Constant;
import com.luck.feign.RenrenFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Liuyunda
 * @date 2022/5/2 12:44
 * @email man021436@163.com
 */
@Service
@Slf4j
public class MQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 发送扣减扣减库存，保存抽奖记录
     * @param msg
     */
    public void sendStock(String msg){
        log.info("发送消息:"+msg);
        rabbitTemplate.convertAndSend(Constant.EXCHANGE,"luckStock.message",msg);
    }
}
