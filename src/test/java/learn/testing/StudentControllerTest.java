package learn.testing;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.User;
import org.apache.catalina.realm.UserDatabaseRealm;
import org.apache.catalina.users.MemoryUserDatabase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {StudentController.class})
@ExtendWith(SpringExtension.class)
class StudentControllerTest {

  @Autowired
  private StudentController studentController;

  @MockBean
  private StudentRepository studentRepository;

  @MockBean
  private StudentService studentService;

  /**
   * Method under test: {@link StudentController#addStudent(Student)}
   */
  @Test
  void testAddStudent() throws Exception {
    MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.get("/")
        .contentType(MediaType.APPLICATION_JSON);

    ObjectMapper objectMapper = new ObjectMapper();
    MockHttpServletRequestBuilder requestBuilder = contentTypeResult
        .content(objectMapper.writeValueAsString(new Student()));
    ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(studentController)
        .build()
        .perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  /**
   * Method under test: {@link StudentController#addStudent(Student)}
   */
  @Test
  void testAddStudent2() throws Exception {
    User user = mock(User.class);
    when(user.getName()).thenReturn("Name");
    UserDatabaseRealm.UserDatabasePrincipal principal = new UserDatabaseRealm.UserDatabasePrincipal(
        user,
        new MemoryUserDatabase());

    MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/");
    getResult.principal(principal);
    MockHttpServletRequestBuilder contentTypeResult = getResult.contentType(
        MediaType.APPLICATION_JSON);

    ObjectMapper objectMapper = new ObjectMapper();
    MockHttpServletRequestBuilder requestBuilder = contentTypeResult
        .content(objectMapper.writeValueAsString(new Student()));
    ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(studentController)
        .build()
        .perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  /**
   * Method under test: {@link StudentController#deleteStudent(Long)}
   */
  @Test
  void testDeleteStudent() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/{studentId}",
        123L);
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
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/");
    ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(studentController)
        .build()
        .perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  /**
   * Method under test: {@link StudentController#getStudentByEmail(String)}
   */
  @Test
  void testGetStudentByEmail() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/{email}",
        "jane.doe@example.org");
    ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(studentController)
        .build()
        .perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
  }
}

