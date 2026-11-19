package com.alumni_searcher.alumni;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication
public class AlumniApplication {
    public static void main(String[] args) {

        SpringApplication.run(AlumniApplication.class, args);
        System.out.println("Backend is running");

    }


}
