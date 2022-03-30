package com.luck.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * ${comments}
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-03-24 17:20:55
 */
@Data
public class CustomerIntegralEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * $column.comments
	 */
	private Long cId;
	/**
	 * $column.comments
	 */
	private Integer integral;

}
