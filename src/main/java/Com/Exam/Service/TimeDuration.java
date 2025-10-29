package Com.Exam.Service;

import java.time.LocalDateTime;

import Com.Exam.Entity.ExamSchedule;

public class TimeDuration {
	
	public boolean isExamActive(ExamSchedule exam) {
	    LocalDateTime now = LocalDateTime.now();
	    LocalDateTime start = exam.getStartTime();
	    LocalDateTime end = start.plusMinutes(exam.getDurationMinutes());

	    return now.isAfter(start) && now.isBefore(end);
	}


}
