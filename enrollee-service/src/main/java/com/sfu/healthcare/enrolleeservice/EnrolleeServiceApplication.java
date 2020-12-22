package com.sfu.healthcare.enrolleeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableDiscoveryClient
@SpringBootApplication
public class EnrolleeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnrolleeServiceApplication.class, args);
    }

}
