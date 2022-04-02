package com.luck.services;

import com.luck.api.R;
import com.luck.vo.LotteryResult;
import com.luck.vo.LotteryVo;

/**
 * 抽奖接口
 *
 * @author liuyd
 * @date 2022/3/29 15:57
 */
public interface LotteryService {
    R<LotteryResult> lottery(LotteryVo lotteryVo);
}
