package com.luck.vo;

import com.luck.entity.ActivityPrizeEntity;
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
public class ActivityPrizeVo extends ActivityPrizeEntity {
    private String prizeName;
}
