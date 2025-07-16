package com.example.examscore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ExamScoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExamScoreApplication.class, args);
    }

}
