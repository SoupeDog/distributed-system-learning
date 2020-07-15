package org.xavier.account.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class BalanceController extends BaseController{

    @GetMapping(value = "/balance/update", produces = {"application/json;charset=UTF-8"})
    public Boolean accountUpdate(@RequestParam("name") String name,
                                 @RequestParam("delta") BigDecimal delta,
                                 @RequestParam(name = "type", required = false, defaultValue = "true") Boolean type) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (type) {
            String info = String.format("%s 余额变化 %s %s", name, delta.toPlainString(), simpleDateFormat.format(new Date()));
            System.out.println(info);
        } else {
            throw new RuntimeException("余额更新失败");
        }
        return type;
    }
}