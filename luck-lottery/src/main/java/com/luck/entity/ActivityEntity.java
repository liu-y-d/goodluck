package com.luck.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * ${comments}
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-03-24 17:20:55
 */
@Data
@TableName("activity")
public class ActivityEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 活动id
	 */
	@TableId
	private Long activityId;
	/**
	 * 活动名称
	 */
	private String activityName;
	/**
	 * 活动开始日期
	 */

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date activityStartTime;
	/**
	 * 活动结束日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date activityEndTime;
	/**
	 * 参与人数限制 0 不限制 1 限制
	 */
	private Integer participationLimit;
	/**
	 * 参与人数
	 */
	private Integer participationNumber;
	/**
	 * 每个用户参与次数
	 */
	private Integer participationTimes;
	/**
	 * 每个用户参与次数刷新规则 0 不刷新 1 日 2 月
	 */
	private Integer participationTimeRefresh;
	/**
	 * 用户参与成本类型 0 无成本 1 积分
	 */
	private Integer participationCostType;
	/**
	 * 用户参与成本
	 */
	private BigDecimal participationCost;
	/**
	 * 大奖通告限制 0 不限制 1 限制
	 */
	private Integer winNewsLimit;

	/**
	 * 抽奖策略 0 普通 1 winprize 2 动态
	 */
	private Integer strategyType;
	/**
	 * 参与多少次中大奖
	 */
	private Integer winPrizeNumber;
	/**
	 * 是否连抽 0 不允许 1 允许
	 */
	private Integer batchGet;
	/**
	 * 连抽多少次
	 */
	private Integer batchGetNumber;
	private Long defaultPrizeId;
	/**
	 * 活动状态 0 开启 1 关闭 2 暂停
	 */
	private Integer activityStatus;

	/**
	 * 参与者列表
	 */
	private String customers;

	/**
	 * 控制者
	 */
	private Long controllerId;

	/**
	 * 允许重开奖 0 否 1是
	 */
	private int restart;

}
