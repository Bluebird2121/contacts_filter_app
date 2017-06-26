package com.bluebird.contacts.configs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.bluebird.contacts.controllers",
        "com.bluebird.contacts.services"})
@EntityScan("com.bluebird.contacts.domain.entity")
@EnableJpaRepositories( "com.bluebird.contacts.domain.repository")
public class AppConfig {

    public static void main(String[] args) {
        SpringApplication.run(AppConfig.class, args);
    }

}
