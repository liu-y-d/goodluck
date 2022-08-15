package io.renren.modules.app.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.app.entity.ActivityEntity;
import io.renren.modules.app.vo.ActivityConfigDetailVo;
import io.renren.modules.app.vo.ActivityPrizeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ${comments}
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-03-24 17:20:55
 */
@Mapper
public interface ActivityDao extends BaseMapper<ActivityEntity> {
    ActivityConfigDetailVo getConfig(@Param("activityId") Long activityId);
    ActivityPrizeVo getStock(@Param("activityId") Long activityId, @Param("prizeId") Long prizeId);

    List<Long> noStock(@Param("activityId") Long activityId);

    int stopActivity(@Param("activityId") Long activityId);
}
