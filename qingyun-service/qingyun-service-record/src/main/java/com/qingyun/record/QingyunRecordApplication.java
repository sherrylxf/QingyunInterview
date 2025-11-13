package com.qingyun.record;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 打卡记录服务启动类
 *
 * @author qingyun
 */
@SpringBootApplication(scanBasePackages = "com.qingyun")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.qingyun.api.feign")
@MapperScan("com.qingyun.record.mapper")
public class QingyunRecordApplication {

    public static void main(String[] args) {
        SpringApplication.run(QingyunRecordApplication.class, args);
    }
}

