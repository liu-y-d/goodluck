package io.renren.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.app.entity.ActivityPrizeEntity;

import java.util.Map;

/**
 * ${comments}
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-03-28 11:37:59
 */
public interface ActivityPrizeService extends IService<ActivityPrizeEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Boolean deductStock(Long activityId, Long prizeId);
}

