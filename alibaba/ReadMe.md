注册中心需要额外运行，请从 https://nacos.io/zh-cn/docs/quick-start.html 下载并运行后再启动：
- goods-service
- dubbo-business-service

访问：
- http://localhost:8080/http/bind/dubbo?name=tom&goodsName=apple&quantity=2
- http://localhost:8081/dubbo/ctc?name=tom&goodsName=apple&quantity=2

http://192.168.18.12:8081/ctc2?seller=tom&buyer=joy&amount=10