package io.renren.modules.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.app.dao.ConfigPrizeDao;
import io.renren.modules.app.entity.ActivityEntity;
import io.renren.modules.app.entity.ActivityPrizeVO;
import io.renren.modules.app.service.ActivityService;
import io.renren.modules.app.service.StockService;
import io.renren.modules.app.vo.ActivityConfigVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;



/**
 * ${comments}
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-03-24 17:20:55
 */
@RestController
@RequestMapping("activity")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @Autowired
    private StockService stockService;

    @Autowired
    private ConfigPrizeDao configPrizeDao;


    /**
     * 列表
     */
    @GetMapping("/page")
    public R page(@RequestParam Map<String, Object> params){
        PageUtils page = activityService.queryPage(params);
        return R.ok(200).put("data", page);
    }
    @GetMapping("/list")
    public R page(){
        List<ActivityEntity> value = activityService.getBaseMapper().selectList(new QueryWrapper<ActivityEntity>());
        return R.ok(200).put("data", value);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{activityId}")
    public R info(@PathVariable("activityId") Long activityId){
		ActivityEntity activity = activityService.getById(activityId);

        return R.ok().put("activity", activity);
    }


    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody ActivityEntity activity){
		activityService.updateById(activity);

        return R.ok();
    }

    /**
     * 删除
     */
    @GetMapping("/delete")
    public R delete(@RequestBody Long[] activityIds){
		activityService.removeByIds(Arrays.asList(activityIds));

        return R.ok();
    }
    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody ActivityConfigVo activityConfig){
        activityService.saveConfig(activityConfig);

        return R.ok();
    }
    @GetMapping("/detail/{activityId}")
    public R getConfig(@PathVariable("activityId") Long activityId) {
        return R.ok(200).put("data", activityService.getConfig(activityId));
    }

    @GetMapping("/deduct/stock")
    public R deductStock(@RequestParam("activityId") Long activityId,@RequestParam("prizeId") Long prizeId) {
        String key = "luck_stock:"+"activityId:"+activityId+",prizeId:"+prizeId;
        long stock = stockService.stock(key, 60 * 60, 1, (a, b) -> {
            return activityService.getStock(activityId, prizeId);
        });
        return R.ok(200).put("data", stock);
    }

    @GetMapping("/update/stock")
    public R updateStock(@RequestParam("activityId") Long activityId,@RequestParam("prizeId") Long prizeId) {
        return R.ok(200).put("data", activityService.updateStock(activityId,prizeId));
    }

    @GetMapping("/nostock/{activityId}")
    public R nostock(@PathVariable("activityId") Long activityId) {
        return R.ok(200).put("data", activityService.noStock(activityId));
    }

    @PostMapping("/addstock")
    public R stop(@RequestBody List<ActivityPrizeVO> activityPrizeVOS) {
        return R.ok(200).put("data", activityService.addStock(activityPrizeVOS));
    }
    @GetMapping("/resetStock")
    public R resetStock(@RequestParam("activityId")Long activityId) {
        return R.ok(200).put("data", configPrizeDao.resetStock(activityId));
    }

}
