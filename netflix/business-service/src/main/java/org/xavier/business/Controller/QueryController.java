package org.xavier.business.Controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.eureka.CloudEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QueryController {
    @Autowired
    private EurekaClient discoveryClient;
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("/service/list")
    public Object queryServiceList(@RequestParam("name") String name) {
        System.out.println(discoveryClient instanceof CloudEurekaClient);
        // 查询全部 name 对应的节点信息
        List<InstanceInfo> result = discoveryClient.getInstancesByVipAddress(name, false);
        System.out.println(result);
        return result;
    }

    @GetMapping("/service/one")
    public Object queryService(@RequestParam("name") String name) {
        // 查询任意 name 对应的节点信息
        InstanceInfo result  =discoveryClient.getNextServerFromEureka(name,false);
        System.out.println(result);
        return result;
    }

}