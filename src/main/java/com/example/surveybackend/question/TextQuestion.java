package com.example.surveybackend.question;

import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.persistence.Entity;

@Entity
public class TextQuestion extends Question {
    public TextQuestion(String name, String title) {
        super("text", name, title);
    }

    public TextQuestion() {

    }
}
