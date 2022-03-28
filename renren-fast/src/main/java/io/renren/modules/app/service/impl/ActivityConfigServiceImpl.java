package io.renren.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.app.dao.ActivityConfigDao;
import io.renren.modules.app.entity.ActivityConfigEntity;
import io.renren.modules.app.service.ActivityConfigService;
import io.renren.modules.app.service.ConfigPrizeService;
import io.renren.modules.app.vo.ConfigPrizeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;




@Service("activityConfigService")
public class ActivityConfigServiceImpl extends ServiceImpl<ActivityConfigDao, ActivityConfigEntity> implements ActivityConfigService {

    @Autowired
    private ConfigPrizeService configPrizeService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ActivityConfigEntity> page = this.page(
                new Query<ActivityConfigEntity>().getPage(params),
                new QueryWrapper<ActivityConfigEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveConfig(ConfigPrizeVo configPrizeVo) {
        ActivityConfigEntity activityConfigEntity = new ActivityConfigEntity();
        BeanUtils.copyProperties(configPrizeVo,activityConfigEntity);
        this.save(activityConfigEntity);
        configPrizeVo.getPrizeList().forEach(prizeConfig -> {
            prizeConfig.setConfigId(activityConfigEntity.getConfigId());

        });
        configPrizeService.saveBatch(configPrizeVo.getPrizeList());
    }

}