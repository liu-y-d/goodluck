package com.luck.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * ${comments}
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-03-24 17:20:55
 */
@Data
@TableName("customer_activity_detail")
public class CustomerActivityDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 客户id
	 */
	@TableId
	private Long cId;
	/**
	 * 活动id
	 */
	private Long activityId;
	/**
	 * 奖品id
	 */
	private Long prizeId;
	/**
	 * 参加时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date joinTime;

}
