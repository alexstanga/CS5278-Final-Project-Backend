package com.example.surveybackend.question;

import com.fasterxml.jackson.databind.util.JSONPObject;

// Utilizing the Factory Method Pattern, we will have
// an abstract class for Question types that will be extended
// with concrete question classes such as TextQuestion
// and a Factory class to create questions based on type
public abstract class Question {
    private String type;
    private String name;
    private String title;

    public Question(String type, String name, String title){
        this.type = type;
        this.name = name;
        this.title = title;
    }

    public String getType() { return type; }
    public String getName() { return name; }
    public String getTitle() { return title; }
}
