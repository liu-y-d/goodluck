package com.luck.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author Liuyunda
 * @date 2022/5/2 13:10
 * @email man021436@163.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LotteryStockMessage {
    private Long cId;

    private Long activityId;

    private List<Long> prizeIds;

    private String joinTime;
}
