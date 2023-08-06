package com.example.springdatabaseintegrationtest;

import com.example.springdatabaseintegrationtest.models.HistoryGrade;
import com.example.springdatabaseintegrationtest.models.MathGrade;
import com.example.springdatabaseintegrationtest.models.ScienceGrade;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class SpringDatabaseIntegrationTestApplication {

    @Bean
    @Scope(value = "prototype")
    @Qualifier("mathGrades")
    MathGrade getMathGrade() {
        return new MathGrade();
    }

    @Bean
    @Scope(value = "prototype")
    @Qualifier("scienceGrades")
    ScienceGrade getScienceGrade() {
        return new ScienceGrade();
    }

    @Bean
    @Scope(value = "prototype")
    @Qualifier("historyGrades")
    HistoryGrade getHistoryGrade() {
        return new HistoryGrade();
    }

    public static void main(String[] args) {

        SpringApplication.run(SpringDatabaseIntegrationTestApplication.class, args);
    }

}
