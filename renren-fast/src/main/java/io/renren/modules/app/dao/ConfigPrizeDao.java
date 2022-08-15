package io.renren.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.app.entity.ActivityPrizeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * ${comments}
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-03-28 11:37:59
 */
@Mapper
public interface ConfigPrizeDao extends BaseMapper<ActivityPrizeEntity> {

    int deductStock(@Param("activityId") Long activityId,@Param("prizeId") Long prizeId);

    int resetStock(@Param("activityId") Long activityId);
}
