package org.xavier.business.controller;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xavier.dubbo.service.OrderService;
import org.xavier.dubbo.service.StockService;

import java.util.UUID;

@RestController
public class DubboBusinessController extends BaseController {
    @Reference
    private OrderService orderService;
    @Reference
    private StockService stockService;

    @GetMapping("/dubbo/ctc")
    public Object ctc(@RequestParam("name") String name,
                      @RequestParam("goodsName") String goodsName,
                      @RequestParam("quantity") Integer quantity) {
        quantity = Math.abs(quantity);
        String orderNo = UUID.randomUUID().toString().replace("-", "");
        orderService.saveOrder(orderNo, name, goodsName, quantity);
        stockService.updateStock(goodsName, -quantity, orderNo);
        return "success with Dubbo Consumer";
    }
}