package io.renren.modules.app.service.impl;

import io.renren.modules.app.dao.PrizeDao;
import io.renren.modules.app.entity.PrizeEntity;
import io.renren.modules.app.service.PrizeService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;



@Service("prizeService")
public class PrizeServiceImpl extends ServiceImpl<PrizeDao, PrizeEntity> implements PrizeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PrizeEntity> page = this.page(
                new Query<PrizeEntity>().getPage(params),
                new QueryWrapper<PrizeEntity>()
        );

        return new PageUtils(page);
    }

}