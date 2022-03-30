package com.luck.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author liuyd
 * @date 2022/3/30 17:38
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ConfigDetailVo extends ActivityConfigEntity {
    private List<ConfigPrizeVo> prizeList;
}
