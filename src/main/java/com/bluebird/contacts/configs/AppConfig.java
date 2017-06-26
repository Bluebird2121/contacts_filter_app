package com.bluebird.contacts.configs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.bluebird.contacts.controllers"})
public class AppConfig {

    public static void main(String[] args) {
        SpringApplication.run(AppConfig.class, args);
    }

}
