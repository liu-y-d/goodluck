package com.luck.services.impl;

import com.luck.api.R;
import com.luck.entity.CustomerIntegralEntity;
import com.luck.feign.RenrenFeignClient;
import com.luck.services.LotteryService;
import com.luck.template.IDrawExec;
import com.luck.utils.AuthUtil;
import com.luck.vo.LotteryResult;
import com.luck.vo.LotteryVo;
import com.luck.vo.LuckUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private IDrawExec iDrawExec;
    @Override
    public R<LotteryResult> lottery(LotteryVo lotteryVo) {
        // 获取当前登录对象
        LuckUser user = AuthUtil.getUser();
        lotteryVo.setUserId(user.getCId());
        // 验证帐号积分
        R<CustomerIntegralEntity> info = renrenFeignClient.info(user.getCId());
        // 进行抽奖
        LotteryResult lotteryResult = iDrawExec.doDrawExec(lotteryVo);
        return R.data(lotteryResult);
    }
}
