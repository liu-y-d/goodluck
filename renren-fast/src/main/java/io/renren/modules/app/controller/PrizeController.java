package io.renren.modules.app.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.app.entity.PrizeEntity;
import io.renren.modules.app.service.PrizeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 抽奖模块
 *
 * @author liuyd
 * @date 2022/3/22 17:58
 */
@RestController
@RequestMapping("/activity/prize")
@Api("奖品管理")
public class PrizeController {
    @Autowired
    private PrizeService prizeService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation("奖品列表")
    // @RequiresPermissions("generator:prize:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = prizeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{prizeId}")
    @ApiOperation("奖品信息")
    // @RequiresPermissions("generator:prize:info")
    public R info(@PathVariable("prizeId") Integer prizeId){
        PrizeEntity prize = prizeService.getById(prizeId);

        return R.ok().put("prize", prize);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation("保存奖品")
    // @RequiresPermissions("generator:prize:save")
    public R save(@RequestBody PrizeEntity prize){
        prizeService.save(prize);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation("修改奖品")
    // @RequiresPermissions("generator:prize:update")
    public R update(@RequestBody PrizeEntity prize){
        prizeService.updateById(prize);

        return R.ok();
    }

    /**
     * 删除
     */
    @GetMapping("/delete")
    @ApiOperation("删除奖品")
    // @RequiresPermissions("generator:prize:delete")
    public R delete(@RequestBody Integer[] prizeIds){
        prizeService.removeByIds(Arrays.asList(prizeIds));
        return R.ok();
    }

}
