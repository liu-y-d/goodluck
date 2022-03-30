package com.luck.services.impl;

import com.luck.api.R;
import com.luck.feign.RenrenFeignClient;
import com.luck.services.LotteryService;
import com.luck.utils.AuthUtil;
import com.luck.vo.CustomerIntegralEntity;
import com.luck.vo.LotteryVo;
import com.luck.vo.LuckUser;
import com.luck.vo.PrizeVo;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RenrenFeignClient renrenFeignClient;
    @Override
    public R<List<PrizeVo>> lottery(LotteryVo lotteryVo) {
        // 获取当前登录对象
        LuckUser user = AuthUtil.getUser();
        // 验证帐号积分
        R<CustomerIntegralEntity> info = renrenFeignClient.info(user.getCId());
        // 获取相关抽奖策略
        // 进行抽奖
        return null;
    }
}
