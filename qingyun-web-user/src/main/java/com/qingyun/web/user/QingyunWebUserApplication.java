package com.qingyun.web.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 用户端后端启动类
 *
 * @author qingyun
 */
@SpringBootApplication(scanBasePackages = "com.qingyun")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.qingyun.api.feign")
public class QingyunWebUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(QingyunWebUserApplication.class, args);
    }
}

