package com.luck;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Liuyunda
 * @date 2022/3/25 1:31
 * @email man021436@163.com
 */
@EnableDiscoveryClient
@SpringBootApplication
public class LuckGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(LuckGatewayApplication.class,args);
    }
}
