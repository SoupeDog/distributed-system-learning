package org.xavier.goods.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xavier.goods.service.OrderServiceImpl;
import org.xavier.goods.service.StockServiceImpl;

import java.util.UUID;

/**
 * 在该项目 org.xavier.goods.service 包下，已经可以通过 Dubbo 协议对外提供服务了，这个 Controller 在于把 Tomcat 得到的请求导向 Dubbo 逻辑处理
 */
@RestController
public class BindHttpAndDubboController extends BaseController {
    @Autowired
    OrderServiceImpl orderService;
    @Autowired
    StockServiceImpl stockService;

    @GetMapping("/http/bind/dubbo")
    public Object httpBindToDubbo(@RequestParam String name,
                                  @RequestParam String goodsName,
                                  @RequestParam Integer quantity) {
        quantity = Math.abs(quantity);
        String orderNo = UUID.randomUUID().toString().replace("-", "");
        orderService.saveOrder(orderNo, name, goodsName, quantity);
        stockService.updateStock(goodsName, -quantity, orderNo);
        return "success without Dubbo Consumer";
    }

}