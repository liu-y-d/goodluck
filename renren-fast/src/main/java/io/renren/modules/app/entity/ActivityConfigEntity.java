package io.renren.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
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
@TableName("activity_config")
public class ActivityConfigEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 配置id
	 */
	@TableId
	private Long configId;
	/**
	 * 配置名称
	 */
	private String configName;
	/**
	 * 奖品id
	 */
	private Long prizeId;
	/**
	 * 奖品数量
	 */
	private Integer prizeNumber;
	/**
	 * 奖品概率限制 0 动态 1 固定
	 */
	private Integer prizeProbabilityFixed;
	/**
	 * 奖品概率
	 */
	private BigDecimal prizeProbability;
	/**
	 * 是否允许中大奖 0 不允许 1 允许
	 */
	private Integer winPrize;
	/**
	 * 参与多少次中大奖
	 */
	private Integer winPrizeNumber;
	/**
	 * 奖品总数限制 0 不限制 1限制
	 */
	private Integer prizeTotalLimit;
	/**
	 * 奖品总数
	 */
	private Long prizeTotal;

}
