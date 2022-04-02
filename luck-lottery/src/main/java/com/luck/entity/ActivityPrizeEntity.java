package com.luck.entity;

import com.luck.vo.PrizeProbabilityInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * ${comments}
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-03-28 11:37:59
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ActivityPrizeEntity extends PrizeProbabilityInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 活动id
	 */
	private Long activityId;

	/**
	 * 奖品数量
	 */
	private Integer prizeNumber;
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
