package io.renren.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.app.entity.ConfigPrizeEntity;

import java.util.Map;

/**
 * ${comments}
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-03-28 11:37:59
 */
public interface ConfigPrizeService extends IService<ConfigPrizeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

