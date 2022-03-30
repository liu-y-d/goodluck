package io.renren.modules.app.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.app.entity.ActivityConfigEntity;
import io.renren.modules.app.vo.ConfigDetailVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * ${comments}
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-03-24 17:20:55
 */
@Mapper
public interface ActivityConfigDao extends BaseMapper<ActivityConfigEntity> {
    ConfigDetailVo getConfig(@Param("activityId") Long activityId);
}
