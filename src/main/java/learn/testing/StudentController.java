package learn.testing;

import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * java doc 입니다.
 * 주석,,,
 */
@RestController
@RequestMapping(path = "api/v1/students")
@AllArgsConstructor
public class StudentController {


  private final StudentService studentService;

  private final StudentRepository studentRepository;

  @GetMapping
  public ResponseEntity<List<Student>> getAllStudents() {
    return ResponseEntity.status(HttpStatus.OK).body(studentService.getAllStudents());
  }

  @PostMapping
  public ResponseEntity<Student> addStudent(@Valid @RequestBody Student student) {
    Student saved = studentService.addStudent(student);
    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
  }

  @DeleteMapping(path = "{studentId}")
  public void deleteStudent(@PathVariable("studentId") Long studentId) {
    studentService.deleteStudent(studentId);
  }

  @GetMapping("/{email}")
  public ResponseEntity<Student> getStudentByEmail(@PathVariable("email") String email) {
    Student student = studentRepository.findStudentByEmail(email);
    return ResponseEntity.status(HttpStatus.OK).body(student);
  }


}
