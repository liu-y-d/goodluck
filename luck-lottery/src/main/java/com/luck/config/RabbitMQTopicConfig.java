package com.luck.config;

import com.luck.constant.Constant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Liuyunda
 * @date 2022/5/2 12:56
 * @email man021436@163.com
 */
@Configuration
public class RabbitMQTopicConfig {
    @Bean
    public Queue queue(){
        return new Queue(Constant.QUEUE);
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(Constant.EXCHANGE);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(topicExchange()).with(Constant.ROUTINGKEY);
    }
}
