package io.renren.modules.app.service.impl;

import io.renren.modules.app.dao.CustomerActivityDetailDao;
import io.renren.modules.app.entity.CustomerActivityDetailEntity;
import io.renren.modules.app.service.CustomerActivityDetailService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;



@Service("customerActivityDetailService")
public class CustomerActivityDetailServiceImpl extends ServiceImpl<CustomerActivityDetailDao, CustomerActivityDetailEntity> implements CustomerActivityDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CustomerActivityDetailEntity> page = this.page(
                new Query<CustomerActivityDetailEntity>().getPage(params),
                new QueryWrapper<CustomerActivityDetailEntity>()
        );

        return new PageUtils(page);
    }

}