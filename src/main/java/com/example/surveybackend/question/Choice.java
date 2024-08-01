package com.example.surveybackend.question;

import jakarta.persistence.Embeddable;

@Embeddable
public class Choice {
    private String text;
    private int choiceValue;

    public Choice() {

    }

    public Choice(String text, int choiceValue) {
        this.text = text;
        this.choiceValue = choiceValue;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getValue() {
        return choiceValue;
    }

    public void setValue(int choiceValue) {
        this.choiceValue = choiceValue;
    }
}
