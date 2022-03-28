package io.renren.modules.app.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.app.entity.ActivityConfigEntity;
import io.renren.modules.app.service.ActivityConfigService;
import io.renren.modules.app.vo.ConfigPrizeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * ${comments}
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-03-24 17:20:55
 */
@RestController
@RequestMapping("activity/config")
public class ActivityConfigController {
    @Autowired
    private ActivityConfigService activityConfigService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = activityConfigService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{configId}")
    public R info(@PathVariable("configId") Long configId){
		ActivityConfigEntity activityConfig = activityConfigService.getById(configId);

        return R.ok().put("activityConfig", activityConfig);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody ConfigPrizeVo activityConfig){
		activityConfigService.saveConfig(activityConfig);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody ActivityConfigEntity activityConfig){
		activityConfigService.updateById(activityConfig);

        return R.ok();
    }

    /**
     * 删除
     */
    @GetMapping("/delete")
    public R delete(@RequestBody Long[] configIds){
		activityConfigService.removeByIds(Arrays.asList(configIds));

        return R.ok();
    }

}
