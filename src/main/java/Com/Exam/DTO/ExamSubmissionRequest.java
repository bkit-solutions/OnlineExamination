package Com.Exam.DTO;

import java.util.List;

public class ExamSubmissionRequest {

    private String username;
    private List<StudentAnswerDTO> answers;

    // Default constructor
    public ExamSubmissionRequest() {
    }

    // Parameterized constructor (optional, for convenience)
    public ExamSubmissionRequest(String username, List<StudentAnswerDTO> answers) {
        this.username = username;
        this.answers = answers;
    }

    public String getUsername() {
        return username != null ? username.trim() : null;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<StudentAnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<StudentAnswerDTO> answers) {
        this.answers = answers;
    }
}
