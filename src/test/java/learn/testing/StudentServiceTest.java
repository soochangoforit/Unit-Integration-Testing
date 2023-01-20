package learn.testing;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import learn.testing.exception.BadRequestException;
import learn.testing.exception.StudentNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

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
    void testDeleteStudent() {
        // doNothing은 return이 void일 경우 사용
        doNothing().when(studentRepository).deleteById((Long) any());

        // 특정 id 값을 가진 Student가 존재 한다면 (when)
        when(studentRepository.existsById((Long) any())).thenReturn(true);
        // 특정 id 값을 가진 Student를 삭제한다 (when)
        studentService.deleteStudent(123L);

        // repository에서 아이디가 존재하는지 작업을 한다. (then)
        verify(studentRepository).existsById((Long) any());
        // repository에서 아이디를 삭제한다. (then)
        verify(studentRepository).deleteById((Long) any());
    }


    @Test
    void shouldThrowExceptionWhenDeleteDoesNotExistStudent() {
        // 특정 id 값을 가지는 학생이 존재하지 않는다면 (when)
        when(studentRepository.existsById((Long) any())).thenReturn(false);

        // 특정 id로 삭제할 행동을 수행하는 경우, StudentNotFoundException이 발생한다. (then)
        assertThrows(StudentNotFoundException.class, () -> studentService.deleteStudent(123L));
        // repository에서 아이디가 존재하는지 작업을 한다. (then)
        verify(studentRepository).existsById((Long) any());
    }

    @Test
    void willThrowWhenEmailIsTaken() {
        // given
        Student student = new Student(
            "Jameds",
            "james@gmail.com",
            Gender.FEMALE
        );

        given(studentRepository.selectExistsEmail(student.getEmail()))
            .willReturn(true);

        // when
        // then
        assertThatThrownBy(() -> studentService.addStudent(student))
            .isInstanceOf(BadRequestException.class)
            .hasMessageContaining("Email " + student.getEmail() + " taken");

        verify(studentRepository, never()).save(any());
    }

    @Test
    void willNotThrowWhenEmailIsNotTaken() {
        // given
        Student student = new Student(
            "James",
            "tncsk@gmail.com",
            Gender.FEMALE);

        given(studentRepository.selectExistsEmail(student.getEmail()))
            .willReturn(false);

        // when
        studentService.addStudent(student);

        // then
        verify(studentRepository).save(any());
    }


}