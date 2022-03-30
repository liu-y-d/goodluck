package com.luck.vo;

import lombok.Data;

/**
 * 抽奖对象
 *
 * @author liuyd
 * @date 2022/3/29 15:44
 */
@Data
public class LotteryVo {

    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 抽取次数
     */
    private Integer getTimes;
}
