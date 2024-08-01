package com.example.surveybackend.question;

import java.util.List;

public class RadioGroupQuestion extends Question {
    private List<String> choices;

    public RadioGroupQuestion(String name, String title, List<String> choices) {
        super("radiogroup", name, title);
        this.choices = choices;
    }

    public List<String> getChoices() { return choices; }
}
