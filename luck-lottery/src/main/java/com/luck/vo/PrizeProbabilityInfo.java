package com.luck.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 奖品概率信息，奖品编号、概率
 * @author liuyd
 * @date 2022/4/1 11:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrizeProbabilityInfo {
    /**
     * 奖品id
     */
    private Long prizeId;

    /**
     * 中奖概率
     */
    private BigDecimal prizeProbability;
}
