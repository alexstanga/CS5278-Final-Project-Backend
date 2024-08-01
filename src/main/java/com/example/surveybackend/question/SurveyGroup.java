package com.example.surveybackend.question;

import java.util.ArrayList;
import java.util.List;

public class SurveyGroup {
    private List<Question> questions;

    public SurveyGroup() {
        this.questions = new ArrayList<>();
    }

    public List<Question> getQuestions() { return questions; }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }
}
