package com.luck.vo;

import com.luck.constant.Constant;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 抽奖结果
 *
 * @author liuyd
 * @date 2022/4/1 11:55
 */
@Data
@AllArgsConstructor
public class LotteryResult {
    private Long userId;
    private String userName;

    private Long activityId;

    private Integer drawState = Constant.DrawState.FAIL.getCode();

    private List<PrizeVo> prizes;
}
