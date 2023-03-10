package com.luck.controller;

import com.luck.api.R;
import com.luck.entity.ActivityEntity;
import com.luck.feign.RenrenFeignClient;
import com.luck.vo.ActivityConfigDetailVo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 活动控制器
 *
 * @author liuyd
 * @date 2022/3/30 16:54
 */
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ActivityController {
    private final RenrenFeignClient renrenFeignClient;
    @GetMapping("/activity/list")
    public R<List<ActivityEntity>> getActivityList() {
        return renrenFeignClient.activityList();
    }
    @GetMapping("/activity/{activityId}")
    public R<ActivityConfigDetailVo> getActivityList(@PathVariable("activityId") Long activityId) {
        return renrenFeignClient.getConfig(activityId);
    }
}
