package com.example.springdatabaseintegrationtest;

import com.example.springdatabaseintegrationtest.models.CollegeStudent;
import com.example.springdatabaseintegrationtest.models.HistoryGrade;
import com.example.springdatabaseintegrationtest.models.MathGrade;
import com.example.springdatabaseintegrationtest.models.ScienceGrade;
import com.example.springdatabaseintegrationtest.reposiotry.HistoryGradeDao;
import com.example.springdatabaseintegrationtest.reposiotry.MathGradeDao;
import com.example.springdatabaseintegrationtest.reposiotry.ScienceGradeDao;
import com.example.springdatabaseintegrationtest.reposiotry.StudentDao;
import com.example.springdatabaseintegrationtest.service.StudentAndGradeService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

    @TestPropertySource("/application-test.properties")
@SpringBootTest
class SpringDatabaseIntegrationTestApplicationTests {

    @Autowired
    private StudentDao studentDao;
    @Autowired
    private StudentAndGradeService studentService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MathGradeDao mathGradeDao;

    @Autowired
    private ScienceGradeDao scienceGradeDao;

    @Autowired
    private HistoryGradeDao historyGradeDao;

    @Value("${sql.script.create.student}")
    private String sqlAddStudent;

    @Value("${sql.script.create.math.grade}")
    private String sqlAddMathGrade;

    @Value("${sql.script.create.science.grade}")
    private String sqlAddScienceGrade;

    @Value("${sql.script.create.history.grade}")
    private String sqlAddHistoryGrade;

    @Value("${sql.script.delete.student}")
    private String sqlDeleteStudent;

    @Value("${sql.script.delete.math.grade}")
    private String sqlDeleteMathGrade;

    @Value("${sql.script.delete.science.grade}")
    private String sqlDeleteScienceGrade;

    @Value("${sql.script.delete.history.grade}")
    private String sqlDeleteHistoryGrade;

    @BeforeEach
    public void setupDataBase() {
        jdbcTemplate.execute(sqlAddStudent);
        jdbcTemplate.execute(sqlAddMathGrade);
        jdbcTemplate.execute(sqlAddScienceGrade);
        jdbcTemplate.execute(sqlAddHistoryGrade);
    }

    @AfterEach
    public void setupAfterTransaction() {
        jdbcTemplate.execute(sqlDeleteStudent);
        jdbcTemplate.execute(sqlDeleteMathGrade);
        jdbcTemplate.execute(sqlDeleteScienceGrade);
        jdbcTemplate.execute(sqlDeleteHistoryGrade);
    }

    @Test
    public void createStudentService() {
        // set up
//       studentService.createStudent("Hwan","Dev","DevHwan@gmail.com");

        // execute
        CollegeStudent student = studentDao.findByEmailAddress("DevHwan@gmail.com");

        // assert
        assertEquals("DevHwan@gmail.com", student.getEmailAddress(), "find by email address");
    }

    @Test
    public void isStudentNullCheck() {
        assertTrue(studentService.checkIfStudentIsNull(1));
        assertFalse(studentService.checkIfStudentIsNull(0));
    }

    @Test
    public void deleteStudentService() {
        Optional<CollegeStudent> student = studentDao.findById(1);

        assertTrue(student.isPresent(), "Return True");

        studentService.deleteStudent(1);

        student = studentDao.findById(1);

        assertFalse(student.isPresent(), "Return False");
    }

    @Sql("/insertData.sql")
    @Test
    public void getGradeBookService() {
        Iterable<CollegeStudent> iterableCollegeStudents = studentService.getGradeBook();

        List<CollegeStudent> collegeStudents = new ArrayList<>();

        for (CollegeStudent collegeStudent : iterableCollegeStudents) {
            collegeStudents.add(collegeStudent);
        }

        assertEquals(5, collegeStudents.size());
    }

    @Test
    public void createGradeService() {
        // create the grade
        assertTrue(studentService.createGrade(80.50, 1, "math"));
        assertTrue(studentService.createGrade(80.50, 1, "science"));
        assertTrue(studentService.createGrade(80.50, 1, "history"));

        // get all grade with studentId
        Iterable<MathGrade> mathGrades = mathGradeDao.findMathGradeByStudentId(1);
        Iterable<ScienceGrade> scienceGrades = scienceGradeDao.findScienceGradeByStudentId(1);
        Iterable<HistoryGrade> historyGrades = historyGradeDao.findHistoryGradeByStudentId(1);

        for (MathGrade m : mathGrades) {
            System.out.println("여기 성적입니다");
            System.out.println(m);
        }

        // verify there is grade
        assertTrue(((Collection<MathGrade>) mathGrades).size() == 1, "Student has math grades");
        assertTrue(((Collection<ScienceGrade>) scienceGrades).size() == 1, "Student has science grade");
        assertTrue(((Collection<HistoryGrade>) historyGrades).size() == 1, "Student has history grade");
    }

    @Test
    @DisplayName(value = "invalid grade")
    public void createGradeServiceReturnFalse() {
        assertFalse(studentService.createGrade(105, 1, "math"));
        assertFalse(studentService.createGrade(-5, 1, "math"));
        assertFalse(studentService.createGrade(80, 2, "math"));
        assertFalse(studentService.createGrade(80, 1, "literature"));
    }
}
