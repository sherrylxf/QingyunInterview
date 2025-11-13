package com.qingyun.question;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 题库服务启动类
 *
 * @author qingyun
 */
@SpringBootApplication(scanBasePackages = "com.qingyun")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.qingyun.api.feign")
@MapperScan("com.qingyun.question.mapper")
public class QingyunQuestionApplication {

    public static void main(String[] args) {
        SpringApplication.run(QingyunQuestionApplication.class, args);
    }
}

