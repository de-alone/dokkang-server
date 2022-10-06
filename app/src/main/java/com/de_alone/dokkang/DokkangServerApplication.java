package com.de_alone.dokkang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DokkangServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DokkangServerApplication.class, args);
    }

    @GetMapping("/status")
    public String status() {
        return "{\"status\": \"ok\"}";
    }
}
