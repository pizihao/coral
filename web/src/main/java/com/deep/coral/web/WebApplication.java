package com.deep.coral.web;

import com.deep.coral.web.annotations.ScheduleScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ScheduleScan(packages = "com.deep.coral.web.task")
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}
