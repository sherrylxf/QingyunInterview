package com.qingyun.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 管理员服务启动类
 *
 * @author qingyun
 */
@SpringBootApplication(scanBasePackages = "com.qingyun")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.qingyun.api.feign")
@MapperScan("com.qingyun.admin.mapper")
public class QingyunAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(QingyunAdminApplication.class, args);
    }
}

