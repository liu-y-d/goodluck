package io.renren.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

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
	private Date joinTime;

}
