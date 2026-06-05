package com.undec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.undec")
public class AdapterApplication {
    public static void main(String[] args) {

        SpringApplication.run(AdapterApplication.class, args);
    }
}
