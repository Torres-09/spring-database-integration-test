package com.example.springdatabaseintegrationtest.service;

import com.example.springdatabaseintegrationtest.models.CollegeStudent;
import com.example.springdatabaseintegrationtest.reposiotry.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class StudentAndGradeService {

    @Autowired
    private StudentDao studentDao;

    public void createStudent(String firstName, String lastName, String emailAddress) {
        CollegeStudent student = new CollegeStudent(firstName, lastName, emailAddress);
        studentDao.save(student);
    }

    public boolean checkIfStudentIsNull(int id) {
        Optional<CollegeStudent> student = studentDao.findById(id);
        return student.isPresent();
    }

    public void deleteStudent(int id) {
        if(checkIfStudentIsNull(id)) studentDao.deleteById(id);
    }

    public Iterable<CollegeStudent> getGradeBook() {
        return studentDao.findAll();
    }
}
