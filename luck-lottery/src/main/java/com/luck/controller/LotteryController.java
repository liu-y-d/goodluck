package com.luck.controller;


import com.luck.api.R;
import com.luck.services.LotteryService;
import com.luck.vo.LotteryResult;
import com.luck.vo.LotteryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 抽奖控制器
 *
 * @author liuyd
 * @date 2022/3/29 15:30
 */
@RestController
@RequestMapping("/api")
public class LotteryController {

    @Autowired
    private LotteryService lotteryService;
    @PostMapping("/lottery/get")
    public R<LotteryResult> lottery(@RequestBody LotteryVo lotteryVo) {
        return lotteryService.lottery(lotteryVo);
    }


}
