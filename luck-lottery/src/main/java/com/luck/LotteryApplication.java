package com.luck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author liuyd
 * @date 2022/3/30 16:42
 */
@EnableFeignClients
@SpringBootApplication
public class LotteryApplication {
    public static void main(String[] args) {
        SpringApplication.run(LotteryApplication.class,args);
    }
}
