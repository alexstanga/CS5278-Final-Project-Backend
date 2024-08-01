package com.example.surveybackend.question;

import java.util.List;

public class QuestionFactory {
    public Question createQuestion(String type, String name, String title, List<String> choices) {
        switch(type){
            case "text":
                return new TextQuestion(name, title);
            case "radiogroup":
                return new RadioGroupQuestion(name, title, choices);
            default:
                throw new IllegalArgumentException("Unknown question type");
        }
    }
}
