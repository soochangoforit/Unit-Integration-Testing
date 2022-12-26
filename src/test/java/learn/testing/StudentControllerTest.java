package learn.testing;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {
    @InjectMocks
    private StudentController studentController;

    @Mock
    private StudentService studentService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }


    /**
     * Method under test: {@link StudentController#addStudent(Student)}
     */
    @Test
    void testAddStudent2() throws Exception {
        Student student = new Student();
        student.setEmail("jane.doe@example.org");
        student.setGender(Gender.MALE);
        student.setId(123L);
        student.setName("Name");
        String content = (new ObjectMapper()).writeValueAsString(student);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(content);

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(studentController)
            .build()
            .perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link StudentController#getAllStudents()}
     */
    @Test
    void testGetAllStudents() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/students");

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.studentController)
            .build()
            .perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk());
    }




    /**
     * Method under test: {@link StudentController#deleteStudent(Long)}
     */
    @Test
    void testDeleteStudent() throws Exception {
      // given
      MockHttpServletRequestBuilder requestBuilder =
          MockMvcRequestBuilders.delete("/api/v1/students/{studentId}", 1L);

      // when
      ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.studentController)
          .build()
          .perform(requestBuilder);

      // then
      actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk());
    }

}

