package io.renren.modules.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luck.utils.AuthUtil;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.app.entity.CustomerActivityDetailEntity;
import io.renren.modules.app.service.CustomerActivityDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



/**
 * ${comments}
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-03-24 17:20:55
 */
@RestController
@RequestMapping("customeractivitydetail")
public class CustomerActivityDetailController {
    @Autowired
    private CustomerActivityDetailService customerActivityDetailService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = customerActivityDetailService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{cId}")
    public R info(@PathVariable("cId") Long cId){
		CustomerActivityDetailEntity customerActivityDetail = customerActivityDetailService.getById(cId);

        return R.ok().put("customerActivityDetail", customerActivityDetail);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody CustomerActivityDetailEntity customerActivityDetail){
		customerActivityDetailService.save(customerActivityDetail);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody CustomerActivityDetailEntity customerActivityDetail){
		customerActivityDetailService.updateById(customerActivityDetail);

        return R.ok();
    }

    /**
     * 删除
     */
    @GetMapping("/delete")
    public R delete(@RequestBody Long[] cIds){
		customerActivityDetailService.removeByIds(Arrays.asList(cIds));

        return R.ok();
    }


    @PostMapping("/saveJoinDetail")
    public R saveJoinDetail(@RequestParam Map<String, Object> params){
        Long cId = AuthUtil.getUser().getCId();
        List<String> prizeIds = Arrays.asList(((String) params.get("prizeIds")).split(","));
        List<CustomerActivityDetailEntity> collect = prizeIds.stream().map(p -> {
            CustomerActivityDetailEntity customerActivityDetailEntity = new CustomerActivityDetailEntity();
            customerActivityDetailEntity.setCId(cId);
            customerActivityDetailEntity.setActivityId(Long.valueOf((String) params.get("activityId")));
            customerActivityDetailEntity.setPrizeId(Long.valueOf(p));
            customerActivityDetailEntity.setJoinTime(new Date());
            return customerActivityDetailEntity;
        }).collect(Collectors.toList());
        boolean flag = customerActivityDetailService.saveBatch(collect);
        return R.ok(200).put("data",flag);
    }
    @GetMapping(value = "/joinActivityDetail" , produces = "application/json;charset=utf-8")
    public R joinActivityDetail(@RequestParam Map<String, Object> params){
        Long activityId = Long.valueOf((String) params.get("activityId"));
        Long cId = AuthUtil.getUser().getCId();
        List<CustomerActivityDetailEntity> value = customerActivityDetailService.getBaseMapper().selectList(new QueryWrapper<CustomerActivityDetailEntity>().eq("activity_id", activityId).eq("c_id", cId));
        return R.ok(200).put("data", value);
    }
}
