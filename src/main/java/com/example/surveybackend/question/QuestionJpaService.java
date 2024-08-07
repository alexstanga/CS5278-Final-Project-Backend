package com.example.surveybackend.question;

import com.example.surveybackend.jpa.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Service class for managing survey questions in a JPA context.
 */
@Service
public class QuestionJpaService {

    @Autowired
    private QuestionRepository questionRepository;

    /**
     * Initializes the database with sample questions.
     * This method is called by the QuestionJpaResource to populate the repository with sample data.
     */
    public void init() {
        for (int i = 1; i <= 2; i++) {
            Question question;
            if (i%2 == 0) {
                question = new TextQuestion("question" + i, "Question" + i);
            } else {
                question = new RadioGroupQuestion(
                        "quetion" + i,
                        "Question" + i,
                        Arrays.asList(new Choice("Option A", 1),
                                new Choice("Option B", 2),
                                new Choice("Option C", 3))
                );
            }
            questionRepository.save(question);
        }

    }
}
