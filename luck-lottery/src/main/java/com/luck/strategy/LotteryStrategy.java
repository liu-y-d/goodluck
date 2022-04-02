package com.luck.strategy;

import com.luck.vo.ActivityConfigDetailVo;
import com.luck.entity.PrizeEntity;

import java.util.List;

/**
 * 抽奖策略接口
 *
 * @author liuyd
 * @date 2022/3/31 10:52
 */
public interface LotteryStrategy {
    List<PrizeEntity> lottery(ActivityConfigDetailVo detailVo,Long userId);

}
