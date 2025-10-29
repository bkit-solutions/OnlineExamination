package Com.Exam.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Com.Exam.Entity.ExamSchedule;

public interface ExamScheduleRepository extends JpaRepository<ExamSchedule, Long> {
    ExamSchedule findByExamTitle(String title);
}