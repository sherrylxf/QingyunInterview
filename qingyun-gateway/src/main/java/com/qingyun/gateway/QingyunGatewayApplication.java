package com.qingyun.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 网关服务启动类
 *
 * @author qingyun
 */
@SpringBootApplication(scanBasePackages = "com.qingyun")
@EnableDiscoveryClient
public class QingyunGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(QingyunGatewayApplication.class, args);
    }
}

