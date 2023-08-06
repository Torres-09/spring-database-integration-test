package com.example.springdatabaseintegrationtest.reposiotry;

import com.example.springdatabaseintegrationtest.models.HistoryGrade;
import org.springframework.data.repository.CrudRepository;

public interface HistoryGradeDao extends CrudRepository<HistoryGrade, Integer> {
    Iterable<HistoryGrade> findHistoryGradeByStudentId(int id);
}
