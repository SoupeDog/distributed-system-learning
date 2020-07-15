package org.xavier.dubbo.service;

public interface OrderService {

    /**
     * 保存订单
     *
     * @param orderNo   订单编号
     * @param userName  用户名
     * @param goodsName 商品名
     * @param quantity  商品数量
     * @return 是否成功
     */
    boolean saveOrder(String orderNo, String userName, String goodsName, int quantity);
}