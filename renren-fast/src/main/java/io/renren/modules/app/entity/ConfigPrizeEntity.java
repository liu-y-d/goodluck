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
@TableName("config_prize")
public class ConfigPrizeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 配置id
	 */
	private Long configId;
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
	 * 奖品总数限制 0 不限制 1限制
	 */
	private Integer prizeTotalLimit;
	/**
	 * 奖品总数
	 */
	private Long prizeTotal;

}
