package io.renren.modules.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.app.dao.ActivityDao;
import io.renren.modules.app.entity.ActivityEntity;
import io.renren.modules.app.entity.ActivityPrizeVO;
import io.renren.modules.app.service.ActivityService;
import io.renren.modules.app.service.ActivityPrizeService;
import io.renren.modules.app.service.StockService;
import io.renren.modules.app.vo.ActivityConfigDetailVo;
import io.renren.modules.app.vo.ActivityConfigVo;
import io.renren.modules.app.vo.ActivityPrizeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service("activityConfigService")
public class ActivityServiceImpl extends ServiceImpl<ActivityDao, ActivityEntity> implements ActivityService {

    @Autowired
    private ActivityPrizeService activityPrizeService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StockService stockService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ActivityEntity> page = this.page(
                new Query<ActivityEntity>().getPage(params),
                new QueryWrapper<ActivityEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveConfig(ActivityConfigVo activityConfigVo) {
        ActivityEntity activityConfigEntity = new ActivityEntity();
        BeanUtils.copyProperties(activityConfigVo,activityConfigEntity);
        this.save(activityConfigEntity);
        activityConfigVo.getPrizeList().forEach(prizeConfig -> {
            prizeConfig.setActivityId(activityConfigEntity.getActivityId());
            if (prizeConfig.getPrizeTotalLimit() == 1) {
                prizeConfig.setPrizeStock(prizeConfig.getPrizeTotal());
            }
        });
        activityPrizeService.saveBatch(activityConfigVo.getPrizeList());
    }

    @Override
    public ActivityConfigDetailVo getConfig(Long activityId) {
        String key = "activity_config:activityId:"+activityId;
        String o = ((String) redisTemplate.opsForValue().get(key));
        ActivityConfigDetailVo activityConfigDetailVo = JSONObject.parseObject(o, ActivityConfigDetailVo.class);
        if (activityConfigDetailVo == null || activityConfigDetailVo.getActivityId() == null){
            ActivityConfigDetailVo config = this.baseMapper.getConfig(activityId);
            redisTemplate.opsForValue().set(key,JSON.toJSONString(config),60*60, TimeUnit.SECONDS);
            activityConfigDetailVo = config;
        }
        return activityConfigDetailVo;
    }

    @Override
    public Boolean updateStock(Long activityId, Long prizeId) {
        return activityPrizeService.deductStock(activityId,prizeId);
    }

    @Override
    public List<Long> noStock(Long activityId) {
        return this.baseMapper.noStock(activityId);
    }

    @Override
    public Integer getStock(Long activityId, Long prizeId) {
        int stock;
        ActivityPrizeVo activityPrizeVo = this.baseMapper.getStock(activityId, prizeId);
        stock = activityPrizeVo.getPrizeTotalLimit() == 0 ? -1:activityPrizeVo.getPrizeStock().intValue();
        return stock;
    }

    @Override
    public boolean stopActivity(Long activityId) {

        boolean flag = this.baseMapper.stopActivity(activityId) > 0;
        if (flag) {
            String key = "activity_config:activityId:"+activityId;
            redisTemplate.delete(key);
        }
        return flag;
    }

    @Override
    public boolean addStock(List<ActivityPrizeVO> activityPrizeVOS) {
        Long activityId = activityPrizeVOS.get(0).getActivityId();
        String key = "activity_config:activityId:"+ activityId;
        Object o = redisTemplate.opsForValue().get(key);
        ActivityEntity activityEntity;
        if (o != null ) {
            activityEntity = JSONObject.parseObject(JSON.toJSONString(o), ActivityEntity.class);
        }else {
            activityEntity = getConfig(activityId);
        }
        if (activityEntity.getActivityStatus() != 0) {
            throw new RuntimeException("活动："+activityId+" 状态为："+activityEntity.getActivityStatus()+" 不可修改！");
        }
        activityPrizeVOS.forEach(a->{
            String stockKey = "luck_stock:"+"activityId:"+a.getActivityId()+",prizeId:"+a.getPrizeId();
            stockService.addStock(stockKey,60 * 60L,a.getStock());
        });


        return true;
    }

}