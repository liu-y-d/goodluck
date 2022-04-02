package com.luck.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 奖品
 *
 * @author liuyd
 * @date 2022/3/22 18:06
 */
@Data
public class PrizeEntity implements Serializable {
    private static final long serialVersionUID = -3329589479659492572L;

    private Long prizeId;

    private String prizeName;

}
