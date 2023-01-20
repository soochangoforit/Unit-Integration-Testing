package learn.testing;

import java.util.List;
import learn.testing.exception.BadRequestException;
import learn.testing.exception.StudentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> getAllStudents() {
      return studentRepository.findAll();
    }

    public Student addStudent(Student student) {
      boolean existsEmail = studentRepository
          .selectExistsEmail(student.getEmail());
      if (existsEmail) {
        throw new BadRequestException(
            "Email " + student.getEmail() + " taken");
      }

      return studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
      if(!studentRepository.existsById(studentId)) {
        throw new StudentNotFoundException(
            "Student with id " + studentId + " does not exists");
      }
      studentRepository.deleteById(studentId);
    }

    public void updateStudent(Long studentId, Student student) {
      if(!studentRepository.existsById(studentId)) {
        throw new StudentNotFoundException(
            "Student with id " + studentId + " does not exists");
      }
      studentRepository.save(student);
    }

}
