package learn.testing;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.apache.catalina.User;
import org.apache.catalina.realm.UserDatabaseRealm;
import org.apache.catalina.users.MemoryUserDatabase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {StudentController.class})
@ExtendWith(SpringExtension.class)
@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private StudentController studentController;

  @MockBean
  private StudentService studentService;

  @Autowired
  private MockMvc mockMvc;


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



  @Test
  void testGetAllStudents() throws Exception {
    // given
    doReturn(getStudents()).when(studentService).getAllStudents();

    // when
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.get("/api/v1/students")
            .contentType(MediaType.APPLICATION_JSON)
    );

    // then
    resultActions
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  private List<Student> getStudents() {
    List<Student> students = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      students.add(new Student(
          "James" + i,
          "james" + i + "@gmail.com", Gender.FEMALE));
    }

    return students;
  }



  /**
   * Method under test: {@link StudentController#getAllStudents()}
   */
  @Test
  void testGetAllStudents2() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/");
    ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(studentController)
        .build()
        .perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
  }

}

