package com.example.springdatabaseintegrationtest.reposiotry;

import com.example.springdatabaseintegrationtest.models.CollegeStudent;
import org.springframework.data.repository.CrudRepository;

public interface StudentDao extends CrudRepository<CollegeStudent, Integer> {
    CollegeStudent findByEmailAddress(String emailAddress);
}
