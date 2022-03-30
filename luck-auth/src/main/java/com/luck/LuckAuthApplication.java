package com.luck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Liuyunda
 * @date 2022/3/25 1:31
 * @email man021436@163.com
 */
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class LuckAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(LuckAuthApplication.class,args);
    }
}
