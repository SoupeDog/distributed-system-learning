package org.xavier.dubbo.service;

public interface StockService {

    /**
     * 变更库存
     *
     * @param goodsName 商品名
     * @param delta     变化量
     * @param orderNo   订单号
     * @return 是否成功
     */
    boolean updateStock(String goodsName, int delta, String orderNo);

}