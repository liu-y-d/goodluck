package com.luck.vo;

import lombok.Data;

/**
 * 奖品对象
 *
 * @author liuyd
 * @date 2022/3/29 15:56
 */
@Data
public class PrizeVo {
    private Long prizeId;
    private String prizeName;
    private Integer prizeNumber;
}
