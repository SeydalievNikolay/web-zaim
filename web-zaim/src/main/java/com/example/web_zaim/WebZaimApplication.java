package com.example.web_zaim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.example.web_zaim.model", "com.example.web_zaim.controller", "com.example.web_zaim.service"})
public class WebZaimApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebZaimApplication.class, args);
    }

}
