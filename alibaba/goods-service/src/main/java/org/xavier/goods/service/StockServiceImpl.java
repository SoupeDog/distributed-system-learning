package org.xavier.goods.service;


import org.apache.dubbo.config.annotation.Service;
import org.xavier.dubbo.service.StockService;

@Service
public class StockServiceImpl implements StockService {

    public boolean updateStock(String goodsName, int delta, String orderNo) {
        System.out.println(String.format("订单 %s 商品 %s 库存变更 %d", orderNo, goodsName, delta));
        return true;
    }
}