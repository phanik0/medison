package com.medison;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = "com.medison")
public class MedisonApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedisonApplication.class, args);
    }
}