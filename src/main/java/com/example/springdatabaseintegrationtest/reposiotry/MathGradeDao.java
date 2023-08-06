package com.example.springdatabaseintegrationtest.reposiotry;

import com.example.springdatabaseintegrationtest.models.MathGrade;
import org.springframework.data.repository.CrudRepository;

public interface MathGradeDao extends CrudRepository<MathGrade, Integer> {
    public Iterable<MathGrade> findMathGradeByStudentId(int id);
}
