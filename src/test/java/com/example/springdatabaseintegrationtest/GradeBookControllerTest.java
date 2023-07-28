package com.example.springdatabaseintegrationtest;

import com.example.springdatabaseintegrationtest.models.CollegeStudent;
import com.example.springdatabaseintegrationtest.models.GradebookCollegeStudent;
import com.example.springdatabaseintegrationtest.reposiotry.StudentDao;
import com.example.springdatabaseintegrationtest.service.StudentAndGradeService;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("/application.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class GradeBookControllerTest {

    private static MockHttpServletRequest request;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private StudentAndGradeService studentAndGradeService;

    @Autowired
    private StudentDao studentDao;

    @BeforeAll
    public static void setup() {
        request = new MockHttpServletRequest();
        request.setParameter("firstname", "Chad");
        request.setParameter("lastname", "Darby");
        request.setParameter("emailAddress", "chad.darby@luv2code_school.com");
    }

    @BeforeEach
    public void beforeEach() {
        jdbcTemplate.execute("insert into student(id, firstname, lastname, email_address) " +
                "values (10, 'Eric', 'Roby', 'eric.roby@luv2code_school.com')");
    }

    @AfterEach
    public void setupAfterTransaction() {
        jdbcTemplate.execute("delete from student");
    }

    @Test
    public void getStudentsHttpRequest() throws Exception {
        GradebookCollegeStudent studentOne = new GradebookCollegeStudent("Hwan", "Dev", "DevHwan@gmail.com");
        GradebookCollegeStudent studentTwo = new GradebookCollegeStudent("Tow", "Dev", "Two@gmail.com");

        ArrayList<CollegeStudent> collegeStudentList = new ArrayList<>(Arrays.asList(studentOne, studentTwo));

        when(studentAndGradeService.getGradeBook()).thenReturn(collegeStudentList);

        Assertions.assertIterableEquals(collegeStudentList, studentAndGradeService.getGradeBook());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isOk()).andReturn();
        ModelAndView mav = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "index");
    }

    @Test
    public void createStudentHttpRequest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("firstname", request.getParameterValues("firstname"))
                        .param("lastname", request.getParameterValues("lastname"))
                        .param("emailAddress", request.getParameterValues("emailAddress")))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mav,"index");

        CollegeStudent verifyStudent = studentDao.findByEmailAddress("chad.darby@luv2code_school.com");

        Assertions.assertNotNull(verifyStudent, "database don't have this student");
    }
}
