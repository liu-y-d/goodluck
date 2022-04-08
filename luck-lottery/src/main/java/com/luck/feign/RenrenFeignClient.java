package com.luck.feign;

import com.luck.api.R;
import com.luck.entity.CustomerActivityDetailEntity;
import com.luck.entity.CustomerIntegralEntity;
import com.luck.utils.PageUtils;
import com.luck.vo.ActivityConfigDetailVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author liuyd
 * @date 2022/3/30 16:58
 */
@FeignClient(value = "renren-fast" ,path = "/renren-fast")
public interface RenrenFeignClient {
    /**
     * 列表
     */
    @GetMapping("/activity/list")
    R<PageUtils> activityList(@RequestParam Map<String, Object> params);
    @GetMapping("/activity/detail/{activityId}")
    R<ActivityConfigDetailVo> getConfig(@PathVariable("activityId") Long activityId);

    @GetMapping("/customer/integral/info/{cId}")
    R<CustomerIntegralEntity> info(@PathVariable("cId") Long cId);
    @GetMapping("/activity/deduct/stock")
    R deductStock(@RequestParam("activityId") Long activityId,@RequestParam("prizeId") Long prizeId);

    @GetMapping("/activity/nostock/{activityId}")
    R<List<Long>> nostock(@PathVariable("activityId") Long activityId);

    @PostMapping("/customeractivitydetail/saveJoinDetail")
    R<Boolean> saveJoinDetail(@RequestParam Map<String, Object> params);

    @GetMapping(value = "/customeractivitydetail/joinActivityDetail", produces = "application/json;charset=utf-8")
    R<List<CustomerActivityDetailEntity>> joinActivityDetail(@RequestParam Map<String, Object> params);
}
