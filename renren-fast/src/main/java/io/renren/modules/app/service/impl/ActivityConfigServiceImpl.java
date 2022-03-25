package io.renren.modules.app.service.impl;

import io.renren.modules.app.dao.ActivityConfigDao;
import io.renren.modules.app.entity.ActivityConfigEntity;
import io.renren.modules.app.service.ActivityConfigService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;




@Service("activityConfigService")
public class ActivityConfigServiceImpl extends ServiceImpl<ActivityConfigDao, ActivityConfigEntity> implements ActivityConfigService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ActivityConfigEntity> page = this.page(
                new Query<ActivityConfigEntity>().getPage(params),
                new QueryWrapper<ActivityConfigEntity>()
        );

        return new PageUtils(page);
    }

}