package com.github.hardlolmaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableHystrix
public class MainExam
{
    public static void main(String[] args)
    {
        SpringApplication.run(MainExam.class);
    }
}
