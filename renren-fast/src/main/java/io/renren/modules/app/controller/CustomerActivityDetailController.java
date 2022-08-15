package io.renren.modules.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luck.utils.AuthUtil;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.app.entity.ActivityEntity;
import io.renren.modules.app.entity.CustomerActivityDetailEntity;
import io.renren.modules.app.service.ActivityService;
import io.renren.modules.app.service.CustomerActivityDetailService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @Autowired
    private ActivityService activityService;
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
        Long cId = Long.valueOf((String) params.get("cId"));
        List<String> prizeIds = Arrays.asList(((String) params.get("prizeIds")).split(","));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<CustomerActivityDetailEntity> collect = prizeIds.stream().map(p -> {
            CustomerActivityDetailEntity customerActivityDetailEntity = new CustomerActivityDetailEntity();
            customerActivityDetailEntity.setCId(cId);
            customerActivityDetailEntity.setActivityId(Long.valueOf((String) params.get("activityId")));
            customerActivityDetailEntity.setPrizeId(Long.valueOf(p));
            String joinTime = ((String) params.get("joinTime"));
            try {
                customerActivityDetailEntity.setJoinTime(simpleDateFormat.parse(joinTime));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            return customerActivityDetailEntity;
        }).collect(Collectors.toList());
        boolean flag = customerActivityDetailService.saveBatch(collect);
        return R.ok(200).put("data",flag);
    }
    @GetMapping(value = "/joinActivityDetail" , produces = "application/json;charset=utf-8")
    public R joinActivityDetail(@RequestParam Map<String, Object> params) throws ParseException {
        Long activityId = Long.valueOf((String) params.get("activityId"));
        Long cId = AuthUtil.getUser().getCId();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ActivityEntity activity = activityService.getById(activityId);
        List<CustomerActivityDetailEntity> value =null;
        if (StringUtils.isNotBlank(activity.getCustomers())) {
            value = customerActivityDetailService.getBaseMapper().selectList(new QueryWrapper<CustomerActivityDetailEntity>().eq("activity_id", activityId));
        }else {
            if (params.get("joinTime") !=null && StringUtils.isNotBlank(params.get("joinTime").toString())){
                Date joinTime = simpleDateFormat.parse((String) params.get("joinTime"));
                value = customerActivityDetailService.getBaseMapper().selectList(new QueryWrapper<CustomerActivityDetailEntity>().eq("activity_id", activityId).eq("c_id", cId).eq("join_time",joinTime));
            }else {
                value = customerActivityDetailService.getBaseMapper().selectList(new QueryWrapper<CustomerActivityDetailEntity>().eq("activity_id", activityId).eq("c_id", cId));
            }
        }
        return R.ok(200).put("data", value);
    }

    @GetMapping(value = "/deleteCustomerJoinDetail" )
    public R deleteCustomerJoinDetail(@RequestParam("activityId") Long activityId) throws ParseException {
        int activity_id = customerActivityDetailService.getBaseMapper().delete(new QueryWrapper<CustomerActivityDetailEntity>().eq("activity_id", activityId));
        return R.ok(200).put("data", activity_id);
    }
}
