package com.example.springdatabaseintegrationtest;

import com.example.springdatabaseintegrationtest.models.CollegeStudent;
import com.example.springdatabaseintegrationtest.reposiotry.StudentDao;
import com.example.springdatabaseintegrationtest.service.StudentAndGradeService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application.properties")
@SpringBootTest
class SpringDatabaseIntegrationTestApplicationTests {

   @Autowired
   private StudentDao studentDao;
   @Autowired
   private StudentAndGradeService studentService;

   @Autowired
   private JdbcTemplate jdbcTemplate;

   @BeforeEach
   public void setupDataBase() {
      jdbcTemplate.execute("insert into student(id, firstname, lastname, email_address) " +
              "values (1, 'Hwan', 'Dev', 'DevHwan@gamil.com')");
   }

   @AfterEach
   public void setupAfterTransaction() {
      jdbcTemplate.execute("delete from student");
   }

   @Test
    public void createStudentService() {
       // set up
       studentService.createStudent("Hwan","Dev","DevHwan@gmail.com");

       // execute
       CollegeStudent student = studentDao.findByEmailAddress("DevHwan@gmail.com");

       // assert
       assertEquals("DevHwan@gmail.com", student.getEmailAddress(),"find by email address");
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

      assertEquals(5,collegeStudents.size());
   }
}
