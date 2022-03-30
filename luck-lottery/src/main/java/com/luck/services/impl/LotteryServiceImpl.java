package com.luck.services.impl;

import com.luck.api.R;
import com.luck.services.LotteryService;
import com.luck.vo.LotteryVo;
import com.luck.vo.PrizeVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 抽奖接口实现类
 *
 * @author liuyd
 * @date 2022/3/29 15:58
 */
@Service
public class LotteryServiceImpl implements LotteryService {

    @Override
    public R<List<PrizeVo>> lottery(LotteryVo lotteryVo) {
        // 获取当前登录对象
        // 验证帐号积分
        // 获取相关抽奖策略
        // 进行抽奖
        return null;
    }
}
