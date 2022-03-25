package io.renren.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.app.entity.PrizeEntity;

import java.util.Map;

/**
 * ${comments}
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-03-23 14:31:07
 */
public interface PrizeService extends IService<PrizeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

