package Com.Exam.Controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Com.Exam.Entity.Exam;
import Com.Exam.Entity.ExamSchedule;
import Com.Exam.Entity.Question;
import Com.Exam.Entity.Result;
import Com.Exam.Repository.ExamRepository;
import Com.Exam.Repository.ExamScheduleRepository;
import Com.Exam.Repository.QuestionRepository;
import Com.Exam.Repository.ResultRepository;
import Com.Exam.Service.QuestionService;
import Com.Exam.Service.UserService;

@RestController
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @Autowired
    ExamRepository examRepository;

    @Autowired
    private ExamScheduleRepository examScheduleRepo;

    @Autowired
    private QuestionRepository questionRepository;
    
    
    @Autowired
    private ResultRepository resultRepository;
    
    @PostMapping("/test")
    public String test()
    {
    	return "hi i am jagadeesh";
    }
    // Endpoint to add a student
    @PostMapping("/add-student")
    public ResponseEntity<String> addStudent(@RequestParam String name,
                                             @RequestParam String email,
                                             @RequestParam String username,
                                             @RequestParam String password) {
        return userService.createStudent(name, email, username, password);
    }

    // Endpoint to add a teacher
    @PostMapping("/add-teacher")
    public ResponseEntity<String> addTeacher(@RequestParam String name,
                                             @RequestParam String email,
                                             @RequestParam String username,
                                             @RequestParam String password) {
        return userService.createTeacher(name, email, username, password);
    }

    // Endpoint to add a question
    @PostMapping("/add-question")
    public String addQuestion(@RequestParam String examTitle,
                              @RequestParam String questionText,
                              @RequestParam String optionA,
                              @RequestParam String optionB,
                              @RequestParam String optionC,
                              @RequestParam String optionD,
                              @RequestParam String correctOption) {
        // Creating a new Question object
        Question question = new Question(examTitle, questionText, optionA, optionB, optionC, optionD, correctOption);
        questionService.saveQuestion(question);
        return "redirect:/admin-dashboard.html";
    }

    // Endpoint to schedule an exam
    @PostMapping("/schedule-exam")
    public String scheduleExam(@RequestParam String examTitle,
                               @RequestParam String startTime,
                               @RequestParam int durationMinutes) {
        LocalDateTime start = LocalDateTime.parse(startTime); // ISO format: yyyy-MM-ddTHH:mm
        ExamSchedule exam = new ExamSchedule(examTitle, start, durationMinutes);
        examScheduleRepo.save(exam);
        return "redirect:/admin-dashboard.html";
    }

    // Endpoint to create an exam (with Exam details)
    @PostMapping("/admin/create-exam")
    public ResponseEntity<String> createExam(@RequestBody Exam exam) {
        examRepository.save(exam);
        return ResponseEntity.ok("Exam created successfully");
    }

    // Endpoint to add question to a specific exam by ID
    @PostMapping("/admin/add-question/{examId}")
    public ResponseEntity<String> addQuestionToExam(@PathVariable Long examId, @RequestBody Question question) {
    	System.out.println("The exam id is"+examId);
    	System.out.println("The correct answer is"+question.getCorrectAnswer());
        Exam exam = examRepository.findById(examId).orElse(null);
        if (exam == null) return ResponseEntity.badRequest().body("Exam not found");

        question.setExam(exam);
        questionRepository.save(question);
        return ResponseEntity.ok("Question added to exam successfully");
    }

    // Endpoint to get the history of exams
    @GetMapping("/admin/exams")
    public ResponseEntity<List<ExamHistory>> getExamHistory() {
        // Fetch all exams
        List<Exam> exams = examRepository.findAll();

        // Map each Exam object to ExamHistory DTO
        List<ExamHistory> examHistoryList = new ArrayList<>();
        for (Exam exam : exams) {
            ExamHistory examHistory = new ExamHistory();
            examHistory.setExamId(exam.getId());
            examHistory.setExamTitle(exam.getTitle());
            
            // Here you should calculate the total students, passed, and failed based on your logic
            examHistory.setTotalStudents(100);  // Example: Replace with actual logic to calculate the number of students
            examHistory.setPassed(50);  // Example: Replace with actual logic
            examHistory.setFailed(50);  // Example: Replace with actual logic
            
            examHistoryList.add(examHistory);
        }

        return ResponseEntity.ok(examHistoryList);
    }

    // Exam history DTO (Data Transfer Object)
    public static class ExamHistory {
        private Long examId;
        private String examTitle;
        private int totalStudents;
        private int passed;
        private int failed;

        public Long getExamId() {
            return examId;
        }

        public void setExamId(Long examId) {
            this.examId = examId;
        }

        public String getExamTitle() {
            return examTitle;
        }

        public void setExamTitle(String examTitle) {
            this.examTitle = examTitle;
        }

        public int getTotalStudents() {
            return totalStudents;
        }

        public void setTotalStudents(int totalStudents) {
            this.totalStudents = totalStudents;
        }

        public int getPassed() {
            return passed;
        }

        public void setPassed(int passed) {
            this.passed = passed;
        }

        public int getFailed() {
            return failed;
        }

        public void setFailed(int failed) {
            this.failed = failed;
        }
    }
    @GetMapping("/results/all")
    public ResponseEntity<List<Result>> getStudentResults() {
        List<Result> results = resultRepository.findAll();
        return ResponseEntity.ok(results);
    }
}
