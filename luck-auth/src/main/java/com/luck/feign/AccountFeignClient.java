package com.luck.feign;

import com.luck.api.R;
import com.luck.vo.CustomerVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liuyd
 * @date 2022/3/29 16:46
 */
@FeignClient(value = "renren-fast" ,path = "/renren-fast")
public interface AccountFeignClient {
    @GetMapping("/customer/info")
    R<CustomerVo> info(@RequestParam("username") String username);
}
