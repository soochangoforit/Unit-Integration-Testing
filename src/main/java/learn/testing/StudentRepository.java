package learn.testing;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * asd.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, StudentRepositoryCustom {

  @Query(""
      + "SELECT CASE WHEN COUNT(s) > 0 THEN "
      + "TRUE ELSE FALSE END "
      + "FROM Student s "
      + "WHERE s.email = ?1"
  )
  Boolean selectExistsEmail(String email);


  Optional<Student> findByNameAndEmail(String name, String email);


}

