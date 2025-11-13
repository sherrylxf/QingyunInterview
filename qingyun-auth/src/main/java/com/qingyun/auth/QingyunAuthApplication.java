package com.qingyun.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 认证服务启动类
 *
 * @author qingyun
 */
@SpringBootApplication(scanBasePackages = "com.qingyun")
@EnableDiscoveryClient
public class QingyunAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(QingyunAuthApplication.class, args);
    }
}

