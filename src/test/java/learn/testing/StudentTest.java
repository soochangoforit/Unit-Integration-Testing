package learn.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class StudentTest {

  @Test
  void testConstructor() {
    Student actualStudent = new Student("Name", "jane.doe@example.org", Gender.MALE);

    assertEquals("jane.doe@example.org", actualStudent.getEmail());
    assertEquals("Name", actualStudent.getName());
    assertNull(actualStudent.getId());
    assertEquals(Gender.MALE, actualStudent.getGender());
  }
}

