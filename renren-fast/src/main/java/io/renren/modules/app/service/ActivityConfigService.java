package io.renren.modules.app.service;


import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.app.entity.ActivityConfigEntity;
import io.renren.modules.app.vo.ActivityConfigVo;
import io.renren.modules.app.vo.ConfigDetailVo;

import java.util.Map;

/**
 * ${comments}
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-03-24 17:20:55
 */
public interface ActivityConfigService extends IService<ActivityConfigEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveConfig(ActivityConfigVo activityConfigVo);

    ConfigDetailVo getConfig(Long activityId);
}

