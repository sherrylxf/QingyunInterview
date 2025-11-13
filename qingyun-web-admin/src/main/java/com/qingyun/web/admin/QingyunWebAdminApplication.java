package com.qingyun.web.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 管理端后端启动类
 *
 * @author qingyun
 */
@SpringBootApplication(scanBasePackages = "com.qingyun")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.qingyun.api.feign")
public class QingyunWebAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(QingyunWebAdminApplication.class, args);
    }
}

