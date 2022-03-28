package io.renren.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.app.dao.ConfigPrizeDao;
import io.renren.modules.app.entity.ConfigPrizeEntity;
import io.renren.modules.app.service.ConfigPrizeService;
import org.springframework.stereotype.Service;

import java.util.Map;



@Service("configPrizeService")
public class ConfigPrizeServiceImpl extends ServiceImpl<ConfigPrizeDao, ConfigPrizeEntity> implements ConfigPrizeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ConfigPrizeEntity> page = this.page(
                new Query<ConfigPrizeEntity>().getPage(params),
                new QueryWrapper<ConfigPrizeEntity>()
        );

        return new PageUtils(page);
    }

}