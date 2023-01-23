package learn.testing;

import static learn.testing.QStudent.student;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;


/**
 * querydsl을 사용하여 동적쿼리를 구현하는 클래스.
 */

public class StudentRepositoryImpl implements StudentRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  public StudentRepositoryImpl(EntityManager em) {
    this.queryFactory = new JPAQueryFactory(em);
  }

  @Override
  public Student findStudentByEmail(String email) {
    return queryFactory
        .selectFrom(student)
        .where(student.email.eq(email))
        .fetchOne();
  }
}
