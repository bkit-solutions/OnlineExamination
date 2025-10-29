package Com.Exam.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Com.Exam.Entity.Result;

public interface ResultRepository extends JpaRepository<Result, Long> {
	@Query("SELECT r.examId FROM Result r WHERE r.studentUsername = :username")
	List<Long> findExamIdsByStudentUsername(@Param("username") String username);

    List<Result> findByStudentUsername(String studentUsername);
    boolean existsByExamIdAndStudentUsername(Long examId, String studentUsername);
}
