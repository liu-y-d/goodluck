package io.renren.modules.app.service;

/**
 * @author Liuyunda
 * @date 2022/5/1 15:07
 * @email man021436@163.com
 */
public interface IStockCallback {
    /**
     * 获取库存
     * @return
     */
    int getStock(Long activityId,Long prizeId);
}
