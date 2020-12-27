package com.sfu.healthcare.enrolleeservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
@OpenAPIDefinition(info =
    @Info(title = "Enrollees API", version = "1.0", description = "Documentation Enrollees API v1.0"))
public class EnrolleeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnrolleeServiceApplication.class, args);
    }

}
