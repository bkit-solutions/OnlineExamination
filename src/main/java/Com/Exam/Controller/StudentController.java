package Com.Exam.Controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Com.Exam.DTO.StudentAnswerDTO;
import Com.Exam.Entity.Exam;
import Com.Exam.Entity.ExamSchedule;
import Com.Exam.Entity.Question;
import Com.Exam.Entity.Result;
import Com.Exam.Entity.User;
import Com.Exam.Repository.ExamRepository;
import Com.Exam.Repository.ExamScheduleRepository;
import Com.Exam.Repository.QuestionRepository;
import Com.Exam.Repository.ResultRepository;
import Com.Exam.Repository.UserRepository;
import Com.Exam.Service.EmailService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private EmailService emailService;
    
    @Autowired
    private ExamRepository examRepository;
    
    @Autowired
    private ExamScheduleRepository svheduleRepo;
    
    
    @GetMapping("/student/exams")
    public ResponseEntity<List<Exam>> getAvailableExams(@RequestParam String username) {
        List<Exam> allExams = examRepository.findAll();
        List<Long> takenExamIds = resultRepository.findExamIdsByStudentUsername(username);

        List<Exam> availableExams = allExams.stream()
            .filter(exam -> !takenExamIds.contains(exam.getId()))
            .collect(Collectors.toList());

        return ResponseEntity.ok(availableExams);
    }



    // Get all questions for a specific exam
    @GetMapping("/student/exam/{examId}/questions")
    public ResponseEntity<List<Question>> getQuestionsByExamId(@PathVariable Long examId) {
        List<Question> questions = questionRepository.findByExamId(examId);
        return ResponseEntity.ok(questions);
    }
    // Submit exam and calculate result
    @PostMapping("/student/submit-exam/{examId}")
    public ResponseEntity<String> submitExam(
            @PathVariable Long examId,
            @RequestBody Com.Exam.DTO.ExamSubmissionRequest submission
    ) {
        List<StudentAnswerDTO> answers = submission.getAnswers();
        String username = submission.getUsername();

        int totalQuestions = answers.size();
        int correctAnswers = 0;

        for (StudentAnswerDTO answer : answers) {
            Optional<Question> questionOpt = questionRepository.findById(answer.getQuestionId());
            if (questionOpt.isPresent()) {
                Question q = questionOpt.get();
                if (q.getCorrectAnswer().equalsIgnoreCase(answer.getSelectedOption())) {
                    correctAnswers++;
                }
            }
        }

        String status = (correctAnswers >= totalQuestions / 2) ? "PASS" : "FAIL";

        // Grading logic
        String grade;
        double percentage = (double) correctAnswers / totalQuestions;

        if (percentage == 1.0) grade = "A+";
        else if (percentage >= 0.8) grade = "A";
        else if (percentage >= 0.6) grade = "B";
        else if (percentage >= 0.4) grade = "C";
        else grade = "F";

        // Create and save result
        Result result = new Result();
        result.setExamId(examId);
        result.setStudentUsername(username); // ✅ Set dynamically
        result.setCorrectAnswers(correctAnswers);
        result.setTotalQuestions(totalQuestions);
        result.setStatus(status);
        result.setGrade(grade);

        resultRepository.save(result);

        return ResponseEntity.ok("Exam Submitted Successfully. Result: " + status + ", Grade: " + grade);
    }

    // Fetch student's previous results
    @GetMapping("/results")
    public ResponseEntity<List<Result>> getStudentResults(@RequestParam String username) {
        List<Result> results = resultRepository.findByStudentUsername(username);
        return ResponseEntity.ok(results);
    }
  
    @GetMapping("/all")
    public List<ExamSchedule> getAllExams() {
        return svheduleRepo.findAll();
    }
}
