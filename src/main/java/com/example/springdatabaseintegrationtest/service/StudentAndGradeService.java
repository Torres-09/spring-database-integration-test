package com.example.springdatabaseintegrationtest.service;

import com.example.springdatabaseintegrationtest.models.CollegeStudent;
import com.example.springdatabaseintegrationtest.models.HistoryGrade;
import com.example.springdatabaseintegrationtest.models.MathGrade;
import com.example.springdatabaseintegrationtest.models.ScienceGrade;
import com.example.springdatabaseintegrationtest.reposiotry.HistoryGradeDao;
import com.example.springdatabaseintegrationtest.reposiotry.MathGradeDao;
import com.example.springdatabaseintegrationtest.reposiotry.ScienceGradeDao;
import com.example.springdatabaseintegrationtest.reposiotry.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class StudentAndGradeService {

    @Autowired
    private StudentDao studentDao;

    @Autowired
    @Qualifier("mathGrades")
    private MathGrade mathGrade;

    @Autowired
    private MathGradeDao mathGradeDao;

    @Autowired
    @Qualifier("scienceGrades")
    private ScienceGrade scienceGrade;

    @Autowired
    private ScienceGradeDao scienceGradeDao;

    @Autowired
    @Qualifier("historyGrades")
    private HistoryGrade historyGrade;

    @Autowired
    private HistoryGradeDao historyGradeDao;

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

    public boolean createGrade(double grade, int studentId, String gradeType) {
        if (!checkIfStudentIsNull(studentId)) {
            return false;
        }

        if (grade >= 0 && grade <= 100) {
            if (gradeType.equals("math")) {
                mathGrade.setId(1);
                mathGrade.setGrade(grade);
                mathGrade.setStudentId(studentId);
                mathGradeDao.save(mathGrade);
                return true;
            } else if (gradeType.equals("science")) {
                scienceGrade.setId(1);
                scienceGrade.setGrade(grade);
                scienceGrade.setStudentId(studentId);
                scienceGradeDao.save(scienceGrade);
                return true;
            } else if (gradeType.equals("history")) {
                historyGrade.setId(1);
                historyGrade.setGrade(grade);
                historyGrade.setStudentId(studentId);
                historyGradeDao.save(historyGrade);
                return true;
            }
        }
        return false;
    }
}
