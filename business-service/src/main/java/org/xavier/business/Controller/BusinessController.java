package org.xavier.business.Controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.LoadBalancerBuilder;
import com.netflix.loadbalancer.RoundRobinRule;
import com.netflix.loadbalancer.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.xavier.business.service.remote.RemoteAccountServiceFeign;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BusinessController extends BaseController{
    @Autowired
    RemoteAccountServiceFeign remoteAccountServiceFeign;

    @GetMapping(value = "/ctc", produces = {"application/json;charset=UTF-8"})
    public Object ctc2(@RequestParam("seller") String seller,
                       @RequestParam("buyer") String buyer,
                       @RequestParam("amount") BigDecimal amount) {
        amount = amount.setScale(2, RoundingMode.FLOOR).abs();
        // 买家扣钱
        BigDecimal amountBuyer = amount.multiply(new BigDecimal("-1"));
        Boolean requestResultBuyer = remoteAccountServiceFeign.accountUpdate(buyer, amountBuyer, true);
        // 卖家得钱
        BigDecimal amountSeller = amount.multiply(new BigDecimal(0.9)).setScale(2, RoundingMode.FLOOR);
        Boolean requestResultSeller = remoteAccountServiceFeign.accountUpdate(seller, amountSeller, true);
        // 平台抽水
        BigDecimal amountPlatform = amount.subtract(amountSeller);
        Boolean requestResultPlatform = remoteAccountServiceFeign.accountUpdate("system", amountPlatform, true);
        String info = String.format("%b\t%s\t%s\r\n%b\t%s\t%s\r\n%b\tsystem\t%s",
                requestResultBuyer, buyer, amountBuyer.toPlainString(),
                requestResultSeller, seller, amountSeller.toPlainString(),
                requestResultPlatform, amountPlatform.toPlainString());
        System.out.println(info);
        return "success";
    }

//    private static RestTemplate restTemplate = new RestTemplate();
//    @Autowired
//    private EurekaClient discoveryClient;

//    private HttpEntity<Boolean> remoteUpdateBalance(String user, BigDecimal amount, Server server) {
//        URI uri = URI.create(String.format("http://%s:%d/balance/update?name=%s&delta=%s",
//                server.getHost(),
//                server.getPort(),
//                user,
//                amount.toPlainString()));
//        return restTemplate.getForEntity(uri, Boolean.class);
//    }
//
//    @GetMapping(value = "/ctc", produces = {"application/json;charset=UTF-8"})
//    public Object ctc(@RequestParam("seller") String seller,
//                      @RequestParam("buyer") String buyer,
//                      @RequestParam("amount") BigDecimal amount) {
//        amount = amount.setScale(2, RoundingMode.FLOOR).abs();
//        List<InstanceInfo> result = discoveryClient.getInstancesByVipAddress("account-service", false);
//        ArrayList<Server> serverList = new ArrayList<Server>();
//
//        for (InstanceInfo instanceInfo : result) {
//            // 参数样例   192.168.18.12         8080
//            Server server = new Server(instanceInfo.getHostName(), instanceInfo.getPort());
//            serverList.add(server);
//        }
//        ILoadBalancer iLoadBalancer = LoadBalancerBuilder.newBuilder()
//                // 轮询调度本身就是默认值，不传也是它，此处为展示 Builder 配置均衡模式
//                .withRule(new RoundRobinRule())
//                .buildFixedServerListLoadBalancer(serverList);
//        Server server = iLoadBalancer.chooseServer("account-service");
//        // 买家扣钱
//        BigDecimal amountBuyer = amount.multiply(new BigDecimal("-1"));
//        HttpEntity<Boolean> httpEntityBuyer = remoteUpdateBalance(buyer, amountBuyer, server);
//        // 卖家得钱
//        BigDecimal amountSeller = amount.multiply(new BigDecimal(0.9)).setScale(2, RoundingMode.FLOOR);
//        HttpEntity<Boolean> httpEntitySeller = remoteUpdateBalance(seller, amountSeller, server);
//        // 平台抽水
//        BigDecimal amountPlatform = amount.subtract(amountSeller);
//        HttpEntity<Boolean> httpEntityPlatform = remoteUpdateBalance("system", amountPlatform, server);
//        String info = String.format("%b\t%s\t%s\r\n%b\t%s\t%s\r\n%b\tsystem\t%s",
//                httpEntityBuyer.getBody(), buyer, amountBuyer.toPlainString(),
//                httpEntitySeller.getBody(), seller, amountSeller.toPlainString(),
//                httpEntityPlatform.getBody(), amountPlatform.toPlainString());
//        System.out.println(info);
//        return "success";
//    }

}