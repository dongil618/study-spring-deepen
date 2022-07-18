package com.hanghae99.studyspringdeepn2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class StudySpringDeepn2Application {

    public static void main(String[] args) {
        SpringApplication.run(StudySpringDeepn2Application.class, args);
    }

}
