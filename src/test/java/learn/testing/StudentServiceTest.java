package learn.testing;


import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    private StudentService studentService;

    @BeforeEach
    void setUp(){
        studentService = new StudentService(studentRepository);
    }

    @Test
    void canGetAllStudents() {
        //when
        studentService.getAllStudents();

        //then
        verify(studentRepository).findAll();
    }

    @Test
    void addStudent() {
    }

    @Test
    void deleteStudent() {
    }
}