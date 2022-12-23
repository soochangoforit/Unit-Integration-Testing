package learn.testing;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;




    @Test
    void itShouldCheckIfStudentExistsEmail() {
        //given
        Student student = new Student(
                "James",
                "james@gmail.com",
            Gender.FEMALE
        );
        underTest.save(student);

        //when
        Boolean expected = underTest.selectExistsEmail(student.getEmail());

        //then
        assertThat(expected).isTrue();
    }

    @Test
    void itShouldCheckIfStudentEmailDoesNotExists() {
        //given
        String email = "james@gmail.com";

        //when
        Boolean expected = underTest.selectExistsEmail(email);

        //then
        assertThat(expected).isFalse();
    }
}