package Com.Exam.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Com.Exam.Entity.Exam;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    // JpaRepository provides methods like save(), findById(), findAll(), delete(), etc.
}