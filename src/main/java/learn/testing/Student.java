package learn.testing;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 학생.
 */
@Getter
@NoArgsConstructor
@Entity
@Table
public class Student {

  @Id
  @SequenceGenerator(
      name = "student_sequence",
      sequenceName = "student_sequence",
      allocationSize = 1
  )
  @GeneratedValue(
      generator = "student_sequence",
      strategy = GenerationType.SEQUENCE)
  private Long id;


  private String name;

  @Column(nullable = false, unique = true)
  private String email;
  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Gender gender;

  /**
   * ddsss.
   */
  public Student(String name, String email, Gender gender) {
    this.name = name;
    this.email = email;
    this.gender = gender;
  }



  public void updateStudent(String name, String email) {
    this.name = name;
    this.email = email;
  }


  public String getFirstEmail() {
    return email.split("@")[0];
  }
}
