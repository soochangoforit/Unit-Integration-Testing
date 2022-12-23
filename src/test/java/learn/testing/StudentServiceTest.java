package learn.testing;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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

    /**
     * 학생이 성공적으로 추가되었는지 확인
     */
    @Test
    void canAddStudent() {
        // given
        Student student = new Student(
            "James",
            "james@gmail.com",
            Gender.FEMALE
        );

        //when
        studentService.addStudent(student);

        //then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);

        // studentRepository.save(student)로 넘어온 student를 캡쳐한다
        verify(studentRepository).save(studentArgumentCaptor.capture());

        Student capturedStudent = studentArgumentCaptor.getValue();

        // 캡쳐한 결과 값이 student와 같은지 확인
        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void deleteStudent() {
    }
}