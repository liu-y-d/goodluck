package io.renren.modules.app.entity;

import lombok.Data;

/**
 * @author Liuyunda
 * @date 2022/5/1 18:42
 * @email man021436@163.com
 */
@Data
public class ActivityPrizeVO {
    private Long activityId;
    private Long prizeId;
    private Integer stock;
}
