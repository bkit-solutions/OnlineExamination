package Com.Exam.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Com.Exam.Entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByExamTitle(String examTitle);
    
    // ✅ Add this method to fetch questions by exam ID
    List<Question> findByExamId(Long examId);
}