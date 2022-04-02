package io.renren.modules.app.service;


import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.app.entity.ActivityEntity;
import io.renren.modules.app.vo.ActivityConfigDetailVo;
import io.renren.modules.app.vo.ActivityConfigVo;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-03-24 17:20:55
 */
public interface ActivityService extends IService<ActivityEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveConfig(ActivityConfigVo activityConfigVo);

    ActivityConfigDetailVo getConfig(Long activityId);

    Boolean deductStock(Long activityId, Long prizeId);

    List<Long> noStock(Long activityId);
}

