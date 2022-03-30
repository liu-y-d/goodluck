package com.luck.feign;

import com.luck.api.R;
import com.luck.utils.PageUtils;
import com.luck.vo.ConfigDetailVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
    @GetMapping("/activity/config/detail/{activityId}")
    R<ConfigDetailVo> getConfig(@PathVariable("activityId") Long activityId);
}
