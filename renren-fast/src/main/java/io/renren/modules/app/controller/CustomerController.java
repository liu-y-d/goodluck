package io.renren.modules.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.app.entity.CustomerEntity;
import io.renren.modules.app.service.CustomerService;
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
@RequestMapping("customer")
@CrossOrigin
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = customerService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{cId}")
    public R info(@PathVariable("cId") Long cId){
		CustomerEntity customer = customerService.getById(cId);

        return R.ok().put("data", customer);
    }
    /**
     * 信息
     */
    @GetMapping("/info")
    public R info(@RequestParam("username") String username){
        CustomerEntity customer = customerService.getOne(new QueryWrapper<CustomerEntity>().eq("c_phone",username));

        return R.ok(200).put("data", customer);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody CustomerEntity customer){
		customerService.save(customer);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody CustomerEntity customer){
		customerService.updateById(customer);

        return R.ok();
    }

    /**
     * 删除
     */
    @GetMapping("/delete")
    public R delete(@RequestBody Long[] cIds){
		customerService.removeByIds(Arrays.asList(cIds));

        return R.ok();
    }

}
