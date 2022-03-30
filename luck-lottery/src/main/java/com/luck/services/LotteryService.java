package com.luck.services;

import com.luck.api.R;
import com.luck.vo.LotteryVo;
import com.luck.vo.PrizeVo;

import java.util.List;

/**
 * 抽奖接口
 *
 * @author liuyd
 * @date 2022/3/29 15:57
 */
public interface LotteryService {
    R<List<PrizeVo>> lottery(LotteryVo lotteryVo);
}
