package io.renren.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.app.dao.ConfigPrizeDao;
import io.renren.modules.app.entity.ActivityPrizeEntity;
import io.renren.modules.app.service.ActivityPrizeService;
import org.springframework.stereotype.Service;

import java.util.Map;



@Service("configPrizeService")
public class ActivityPrizeServiceImpl extends ServiceImpl<ConfigPrizeDao, ActivityPrizeEntity> implements ActivityPrizeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ActivityPrizeEntity> page = this.page(
                new Query<ActivityPrizeEntity>().getPage(params),
                new QueryWrapper<ActivityPrizeEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public Boolean deductStock(Long activityId, Long prizeId) {
        ActivityPrizeEntity activityPrizeEntity = this.baseMapper.selectOne(new QueryWrapper<ActivityPrizeEntity>().eq("activity_id", activityId).eq("prize_id", prizeId));
        if (activityPrizeEntity != null && activityPrizeEntity.getPrizeTotalLimit() == 0) {
            return true;
        }
        int count = this.baseMapper.deductStock(activityId, prizeId);
        return count == 1;
    }

}