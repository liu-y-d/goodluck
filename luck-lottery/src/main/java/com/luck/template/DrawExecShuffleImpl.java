package com.luck.template;

import com.luck.strategy.LotteryAlgorithmStrategy;
import com.luck.vo.ActivityConfigDetailVo;
import com.luck.vo.ActivityPrizeVo;
import com.luck.vo.LotteryResult;
import com.luck.vo.LotteryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author Liuyunda
 * @date 2022/6/9 0:27
 * @email man021436@163.com
 */
@Slf4j
@Service
public class DrawExecShuffleImpl extends AbstractManualLotteryTemplate{
    @Override
    public LotteryResult doDrawExec(LotteryVo lotteryRequest) {
        return super.doDrawExec(lotteryRequest);
    }

    @Override
    protected void checkController(ActivityConfigDetailVo data, Long userId) {
        if (!data.getControllerId().equals(userId)) {
            throw new RuntimeException("当前用户没有开奖权限！");
        }
    }

    @Override
    protected void checkActivityConfig(ActivityConfigDetailVo data) {
        if (data.getRestart() == 1) {
            if (data.getActivityStatus() == 3) {
                // 删除上次抽奖记录
                this.client.resetStock(data.getActivityId());
                this.client.deleteCustomerJoinDetail(data.getActivityId());
            }
        }else {
            if (data.getActivityStatus() == 3) {
                throw new RuntimeException("ActivityId:"+data.getActivityId()+"，不允许重复开奖！");
            }
        }

    }

    @Override
    protected void drawAlgorithm(Long activityId, LotteryAlgorithmStrategy lotteryStrategy, ActivityConfigDetailVo activityConfig,LotteryVo lotteryRequest) {
        lotteryStrategy.initCustomerList(activityId,activityConfig);
        List<Long> customerIds = lotteryStrategy.randomDraw(activityId, activityConfig);
        List<ActivityPrizeVo> prizeList = activityConfig.getPrizeList();
        List<ActivityPrizeVo> prizeListWhole = new ArrayList<>();
        for (int i = 0; i < prizeList.size(); i++) {
            ActivityPrizeVo prize = prizeList.get(i);
            if (prize.getPrizeTotalLimit() == 1) {
                if (prize.getPrizeTotal() != 0) {
                    for (int j = 0; j < prize.getPrizeTotal(); j++) {
                        prizeListWhole.add(prize);
                    }
                }
            }else {
                throw new RuntimeException("奖品配置不正确，奖品总数必须有限制！");
            }
        }
        for (int i = 0; i < customerIds.size(); i++) {
            Long cId = customerIds.get(i);
            HashMap<String, Object> params = new HashMap<>(4);
            params.put("activityId",activityId.toString());
            ActivityPrizeVo prize = prizeListWhole.get(i);
            Long prizeId = prize.getPrizeId();
            params.put("prizeIds", prizeId);
            params.put("joinTime", lotteryRequest.getTimestamp());
            params.put("cId", cId);
            // 4、记录抽奖信息
            this.client.updateStock(activityId,prizeId);
            this.client.saveJoinDetail(params);
        }
    }
}
