package com.example.surveybackend.question;

import com.example.surveybackend.jpa.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QuestionJpaResource {

    @Autowired
    private QuestionJpaService questionJpaService;

    @Autowired
    private QuestionRepository questionRepository;

    public QuestionJpaResource(QuestionJpaService questionJpaService, QuestionRepository questionRepository){
        this.questionJpaService = questionJpaService;
        this.questionRepository = questionRepository;
    }

    //Initialize our database with questions
    @GetMapping("/jpa/questions/init")
    public void initSurvey(){
        questionJpaService.init();
    }

    //Retrieve all questions from database
    @GetMapping("/jpa/questions")
    public List<Question> retrieveAllQuestions() { return questionRepository.findAll(); }
}
