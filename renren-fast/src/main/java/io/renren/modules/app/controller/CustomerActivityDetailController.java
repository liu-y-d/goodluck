package io.renren.modules.app.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.modules.app.entity.CustomerActivityDetailEntity;
import io.renren.modules.app.service.CustomerActivityDetailService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * ${comments}
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-03-24 17:20:55
 */
@RestController
@RequestMapping("generator/customeractivitydetail")
public class CustomerActivityDetailController {
    @Autowired
    private CustomerActivityDetailService customerActivityDetailService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:customeractivitydetail:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = customerActivityDetailService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{cId}")
    @RequiresPermissions("generator:customeractivitydetail:info")
    public R info(@PathVariable("cId") Long cId){
		CustomerActivityDetailEntity customerActivityDetail = customerActivityDetailService.getById(cId);

        return R.ok().put("customerActivityDetail", customerActivityDetail);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:customeractivitydetail:save")
    public R save(@RequestBody CustomerActivityDetailEntity customerActivityDetail){
		customerActivityDetailService.save(customerActivityDetail);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:customeractivitydetail:update")
    public R update(@RequestBody CustomerActivityDetailEntity customerActivityDetail){
		customerActivityDetailService.updateById(customerActivityDetail);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:customeractivitydetail:delete")
    public R delete(@RequestBody Long[] cIds){
		customerActivityDetailService.removeByIds(Arrays.asList(cIds));

        return R.ok();
    }

}
