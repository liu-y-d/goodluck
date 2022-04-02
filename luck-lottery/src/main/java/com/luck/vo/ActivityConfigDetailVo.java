package com.luck.vo;

import com.luck.entity.ActivityEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author liuyd
 * @date 2022/3/30 17:38
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ActivityConfigDetailVo extends ActivityEntity {
    private List<ActivityPrizeVo> prizeList;
}
