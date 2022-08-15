package com.luck.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

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

    @JsonIgnore
    private Long userId;
    /**
     * 抽取次数
     */
    private Integer getTimes;

    /**
     * 时间戳
     */
    private String timestamp;
}
