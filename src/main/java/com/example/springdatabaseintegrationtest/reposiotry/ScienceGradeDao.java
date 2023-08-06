package com.example.springdatabaseintegrationtest.reposiotry;

import com.example.springdatabaseintegrationtest.models.ScienceGrade;
import org.springframework.data.repository.CrudRepository;

public interface ScienceGradeDao extends CrudRepository<ScienceGrade, Integer> {
    Iterable<ScienceGrade> findScienceGradeByStudentId(int id);
}
