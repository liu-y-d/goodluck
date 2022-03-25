package io.renren.modules.app.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.modules.app.entity.CustomerIntegralEntity;
import io.renren.modules.app.service.CustomerIntegralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
@RequestMapping("customerintegral")
public class CustomerIntegralController {
    @Autowired
    private CustomerIntegralService customerIntegralService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = customerIntegralService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{cId}")
    public R info(@PathVariable("cId") Long cId){
		CustomerIntegralEntity customerIntegral = customerIntegralService.getById(cId);

        return R.ok().put("customerIntegral", customerIntegral);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody CustomerIntegralEntity customerIntegral){
		customerIntegralService.save(customerIntegral);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody CustomerIntegralEntity customerIntegral){
		customerIntegralService.updateById(customerIntegral);

        return R.ok();
    }

    /**
     * 删除
     */
    @GetMapping("/delete")
    public R delete(@RequestBody Long[] cIds){
		customerIntegralService.removeByIds(Arrays.asList(cIds));

        return R.ok();
    }

}
