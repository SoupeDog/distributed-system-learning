package org.xavier.business.hystrix;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class AccountServiceFallBack implements FallbackFactory<Void> {

    @Override
    public Void create(Throwable cause) {
        cause.printStackTrace();
        throw new RuntimeException(String.format("服务降级 %s", cause.getMessage()));
    }
}