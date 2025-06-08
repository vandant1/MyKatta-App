package com.genius.mykatta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class MykattaApplication {

    private static final Logger log = LoggerFactory.getLogger(MykattaApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MykattaApplication.class, args);
        log.info("🚀 MyKatta Spring Boot Application Started Successfully");
    }
}
