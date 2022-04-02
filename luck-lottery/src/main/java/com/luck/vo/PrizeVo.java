package com.luck.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 奖品对象
 *
 * @author liuyd
 * @date 2022/3/29 15:56
 */
@Data
@AllArgsConstructor
public class PrizeVo {
    private Long prizeId;
    private String prizeName;
    private Integer prizeNumber;
}
