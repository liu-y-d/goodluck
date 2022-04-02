package io.renren.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.app.dao.ActivityDao;
import io.renren.modules.app.entity.ActivityEntity;
import io.renren.modules.app.service.ActivityService;
import io.renren.modules.app.service.ActivityPrizeService;
import io.renren.modules.app.vo.ActivityConfigDetailVo;
import io.renren.modules.app.vo.ActivityConfigVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;




@Service("activityConfigService")
public class ActivityServiceImpl extends ServiceImpl<ActivityDao, ActivityEntity> implements ActivityService {

    @Autowired
    private ActivityPrizeService activityPrizeService;
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

        });
        activityPrizeService.saveBatch(activityConfigVo.getPrizeList());
    }

    @Override
    public ActivityConfigDetailVo getConfig(Long activityId) {
        return this.baseMapper.getConfig(activityId);
    }

    @Override
    public Boolean deductStock(Long activityId, Long prizeId) {
        return activityPrizeService.deductStock(activityId,prizeId);
    }

    @Override
    public List<Long> noStock(Long activityId) {
        return this.baseMapper.noStock(activityId);
    }

}