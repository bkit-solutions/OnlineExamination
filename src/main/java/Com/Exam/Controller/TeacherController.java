package Com.Exam.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import Com.Exam.Entity.Result;
import Com.Exam.Repository.ResultRepository;

@RestController
public class TeacherController {
	
	@Autowired
	private ResultRepository resultRepository;
	
	@PostMapping("/teacher/assign-grade")
	public ResponseEntity<String> assignGrade(@RequestBody GradeRequest request) {
	    Optional<Result> optionalResult = resultRepository.findById(request.getResultId());
	    if (optionalResult.isPresent()) {
	        Result result = optionalResult.get();
	        result.setGrade(request.getGrade());
	        resultRepository.save(result);
	        return ResponseEntity.ok("Grade assigned successfully");
	    }
	    return ResponseEntity.badRequest().body("Result not found");
	}

	@GetMapping("/teacher/view-submissions")
	public ResponseEntity<List<Result>> getAllResultsForTeacher() {
	    return ResponseEntity.ok(resultRepository.findAll());
	}

	// DTO
	public static class GradeRequest {
	    private Long resultId;
	    private String grade;
	    // Getters & Setters
		public Long getResultId() {
			return resultId;
		}
		public void setResultId(Long resultId) {
			this.resultId = resultId;
		}
		public String getGrade() {
			return grade;
		}
		public void setGrade(String grade) {
			this.grade = grade;
		}
	    
	    
	}


}
