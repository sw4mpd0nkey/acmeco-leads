package com.acme.leads.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"com.acme.leads.auth", "com.acme.leads.shared"})
@EnableFeignClients({"com.acme.leads.auth", "com.acme.leads.shared"})
@OpenAPIDefinition(
    info =
        @Info(
            title = "Auth API",
            version = "1.0",
            description = "Documentation Auth API v1.0"))
public class AuthServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }
}