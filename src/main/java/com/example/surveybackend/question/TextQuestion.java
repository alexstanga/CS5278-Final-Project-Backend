package com.example.surveybackend.question;

import com.fasterxml.jackson.databind.util.JSONPObject;

public class TextQuestion extends Question {
    public TextQuestion(String name, String title) {
        super("text", name, title);
    }
}
