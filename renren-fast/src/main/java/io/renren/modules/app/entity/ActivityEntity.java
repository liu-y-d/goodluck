package io.renren.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
	 * 配置id
	 */
	private Long configId;
	/**
	 * 活动开始日期
	 */
	private Date activityStartTime;
	/**
	 * 活动结束日期
	 */
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
	 * 活动状态 0 开启 1 关闭
	 */
	private Integer activityStatus;

}
