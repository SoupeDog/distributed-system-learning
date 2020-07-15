package org.xavier.goods.service;


import org.apache.dubbo.config.annotation.Service;
import org.xavier.dubbo.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    public boolean saveOrder(String orderNo, String userName, String goodsName, int quantity) {
        System.out.println(String.format("用户 %s 购买 %s 数量 %d", userName, goodsName, quantity));
        return true;
    }
}