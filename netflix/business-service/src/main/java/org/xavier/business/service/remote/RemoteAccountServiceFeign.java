package org.xavier.business.service.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.xavier.business.hystrix.AccountServiceFallBack;

import java.math.BigDecimal;

/**
 * 指定要调用在 eureka 注册的服务名为 account-service 的服务
 */
@FeignClient(name = "account-service", fallbackFactory = AccountServiceFallBack.class)
public interface RemoteAccountServiceFeign {

    /**
     * 远程调用更新账户余额
     *
     * @param name  账户
     * @param delta 余额变化量
     * @param type  是否成功 false 则失败
     * @return 执行是否成功
     */
    @GetMapping(value = "/balance/update")
    Boolean accountUpdate(@RequestParam("name") String name,
                          @RequestParam("delta") BigDecimal delta,
                          @RequestParam(name = "type", required = false, defaultValue = "true") Boolean type);
}