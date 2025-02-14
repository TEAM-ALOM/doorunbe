package com.alom.dorundorunbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DorundorunBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DorundorunBeApplication.class, args);
    }

}
