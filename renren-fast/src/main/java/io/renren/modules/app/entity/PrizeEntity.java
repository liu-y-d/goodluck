package io.renren.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 奖品
 *
 * @author liuyd
 * @date 2022/3/22 18:06
 */
@Data
@TableName("prize")
public class PrizeEntity implements Serializable {
    private static final long serialVersionUID = -3329589479659492572L;

    @TableId
    private Long prizeId;

    private String prizeName;

}
