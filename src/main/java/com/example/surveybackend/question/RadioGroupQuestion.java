package com.example.surveybackend.question;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;

import java.util.HashMap;
import java.util.List;

@Entity
public class RadioGroupQuestion extends Question {
    @ElementCollection
    @CollectionTable(
            name = "radio_group_question_choices",
            joinColumns = @JoinColumn(name = "radio_group_question_id")
    )
    private List<Choice> choices;

    public RadioGroupQuestion(String name, String title, List<Choice> choices) {
        super("radiogroup", name, title);
        this.choices = choices;
    }

    public RadioGroupQuestion() {

    }

    public List<Choice> getChoices() { return choices; }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }
}
