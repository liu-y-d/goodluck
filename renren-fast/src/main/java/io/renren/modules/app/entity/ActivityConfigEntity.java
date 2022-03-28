package io.renren.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
	 * 是否允许中大奖 0 不允许 1 允许
	 */
	private Integer winPrize;
	/**
	 * 参与多少次中大奖
	 */
	private Integer winPrizeNumber;

}
