package io.renren.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * ${comments}
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-03-28 11:37:59
 */
@Data
@TableName("activity_prize")
public class ActivityPrizeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 活动id
	 */
	private Long activityId;
	/**
	 * 奖品id
	 */
	private Long prizeId;
	/**
	 * 奖品数量
	 */
	private Integer prizeNumber;
	/**
	 * 奖品概率
	 */
	private BigDecimal prizeProbability;
	/**
	 * 奖品总数限制 0 不限制 1限制
	 */
	private Integer prizeTotalLimit;
	/**
	 * 奖品总数
	 */
	private Long prizeTotal;
	/**
	 * 奖品库存
	 */
	private Long prizeStock;

}
