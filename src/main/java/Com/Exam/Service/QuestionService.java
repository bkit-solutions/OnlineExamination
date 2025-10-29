package Com.Exam.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Com.Exam.Entity.Question;
import Com.Exam.Repository.QuestionRepository;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public void saveQuestion(Question q) {
        questionRepository.save(q);
    }
}