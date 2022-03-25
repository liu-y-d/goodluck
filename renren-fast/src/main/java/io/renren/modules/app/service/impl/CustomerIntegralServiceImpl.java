package io.renren.modules.app.service.impl;

import io.renren.modules.app.dao.CustomerIntegralDao;
import io.renren.modules.app.entity.CustomerIntegralEntity;
import io.renren.modules.app.service.CustomerIntegralService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;




@Service("customerIntegralService")
public class CustomerIntegralServiceImpl extends ServiceImpl<CustomerIntegralDao, CustomerIntegralEntity> implements CustomerIntegralService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CustomerIntegralEntity> page = this.page(
                new Query<CustomerIntegralEntity>().getPage(params),
                new QueryWrapper<CustomerIntegralEntity>()
        );

        return new PageUtils(page);
    }

}