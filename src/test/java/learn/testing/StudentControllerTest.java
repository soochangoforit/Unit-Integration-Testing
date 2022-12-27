package learn.testing;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
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



    @Test
    void testAddStudent() throws Exception {
        // given
        Student student = new Student(
          "James",
          "asd@gmail.com", Gender.FEMALE);

        doReturn(student).when(studentService).addStudent(any(Student.class));

        // when
        ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(student))
        );

        // then
        resultActions
            .andExpect(status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("name").value("James"))
            .andExpect(MockMvcResultMatchers.jsonPath("email").value("asd@gmail.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("gender").value(Gender.FEMALE.name()));

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

        actualPerformResult.andExpect(status().isOk());
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
      actualPerformResult.andExpect(status().isOk());
    }

}

