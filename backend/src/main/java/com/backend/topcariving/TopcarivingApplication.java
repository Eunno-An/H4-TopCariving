package com.backend.topcariving;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TopcarivingApplication {

    public static void main(String[] args) {
        SpringApplication.run(TopcarivingApplication.class, args);
    }

}
