package com.ehs.outsera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class OutseraApplication {

    public static void main(String[] args) {
        SpringApplication.run(OutseraApplication.class, args);
    }

}
