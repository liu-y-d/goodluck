package io.renren.modules.app.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.app.entity.CustomerEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * ${comments}
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-03-24 17:20:55
 */
@Mapper
public interface CustomerDao extends BaseMapper<CustomerEntity> {
	
}
