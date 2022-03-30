package io.renren.modules.app.vo;

import io.renren.modules.app.entity.ConfigPrizeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 奖品配置
 *
 * @author liuyd
 * @date 2022/3/28 11:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ConfigPrizeVo extends ConfigPrizeEntity {
    private String prizeName;
}
