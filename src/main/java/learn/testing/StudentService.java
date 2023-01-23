package learn.testing;

import java.util.List;
import learn.testing.exception.BadRequestException;
import learn.testing.exception.StudentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 자바 독입니다. javaDoc.
 */
@AllArgsConstructor
@Service
public class StudentService {

  private final StudentRepository studentRepository;

  public List<Student> getAllStudents() {
    return studentRepository.findAll();
  }


  /**
   * 학생을 추가하는 메소드입니다.
   *
   * @param student 학생.
   * @return 학생.
   */
  public Student addStudent(Student student) {
    boolean existsEmail = studentRepository
        .selectExistsEmail(student.getEmail());
    if (existsEmail) {
      throw new BadRequestException(
          "Email " + student.getEmail() + " taken");
    }

    return studentRepository.save(student);
  }

  /**
   * 주석을 남겨라.
   *
   * @param studentId 학생 아이디.
   */
  public void deleteStudent(Long studentId) {
    if (!studentRepository.existsById(studentId)) {
      throw new StudentNotFoundException(
          "Student with id " + studentId + " does not exists");
    }
    studentRepository.deleteById(studentId);
  }
}
