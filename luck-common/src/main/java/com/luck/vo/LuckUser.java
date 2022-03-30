package com.luck.vo;

import lombok.Data;

/**
 * @author liuyd
 * @date 2022/3/30 19:41
 */
@Data
public class LuckUser {
    private static final long serialVersionUID = 1L;

    /**
     * $column.comments
     */
    private Long cId;
    /**
     * $column.comments
     */
    private String cName;
    /**
     * $column.comments
     */
    private String cPhone;

    private String cPassword;
}
