package com.ascending;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "com.ascending")
public class AppInitializer extends SpringBootServletInitializer {

    public static void main(String[] arg){
        SpringApplication.run(AppInitializer.class, arg);
    }
}
