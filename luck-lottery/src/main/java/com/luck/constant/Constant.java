package com.luck.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author liuyd
 * @date 2022/3/31 15:22
 */
public interface Constant {
    String LOTTERY_STRATEGY_NORMAL = "normal";
    String LOTTERY_STRATEGY_WIN = "win";
    String LOTTERY_STRATEGY_DYNAMIC = "dynamic";

    @AllArgsConstructor
    @Getter
    enum LotteryStrategyEnum {
        LOTTERY_STRATEGY_NORMAL(0,"normal"),
        LOTTERY_STRATEGY_WIN(1,"win"),
        LOTTERY_STRATEGY_DYNAMIC(2,"dynamic");

        private int code;

        private String value;

        public static LotteryStrategyEnum valueOf(int code) {
            for (LotteryStrategyEnum type : LotteryStrategyEnum.values()) {
                if (type.getCode()==code) {
                    return type;
                }
            }
            return null;
        }
    }

    /**
     * 中奖状态：0未中奖、1已中奖、2兜底奖
     */
    @AllArgsConstructor
    @Getter
    enum DrawState {
        /**
         * 未中奖
         */
        FAIL(0, "未中奖"),

        /**
         * 已中奖
         */
        SUCCESS(1, "已中奖"),

        /**
         * 兜底奖
         */
        Cover(2, "兜底奖");

        private Integer code;
        private String info;
    }
}
