package com.luck.template;

import com.luck.vo.LotteryResult;
import com.luck.vo.LotteryVo;

import java.util.List;

/**
 * 执行抽奖接口
 *
 * @author liuyd
 * @date 2022/4/1 11:54
 */
public interface IDrawExec {
    /**
     * 执行抽奖
     * @param lotteryRequest
     * @return
     */
    LotteryResult doDrawExec(LotteryVo lotteryRequest);

}
