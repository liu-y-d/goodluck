package io.renren.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author Liuyunda
 * @date 2022/5/2 14:52
 * @email man021436@163.com
 */
@Configuration
public class RedissonConfig {
    /**
     * @Description: 所有对redisson的使用都是通过RedissonClient对象
     * @Param: []
     * @return: org.redisson.api.RedissonClient
     * @Author: Liuyunda
     * @Date: 2021/4/1
     */
    @Bean
    public RedissonClient redisson() throws IOException {
        // 创建配置
        Config config = new Config();
        // 安全链接用rediss://
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        // 根据config创建出redissonClient实例
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;

    }
}
