package com.example.surveybackend.question;

import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.persistence.*;

// Utilizing the Factory Method Pattern, we will have
// an abstract class for Question types that will be extended
// with concrete question classes such as TextQuestion
// and a Factory class to create questions based on type
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String name;
    private String title;

    public Question(String type, String name, String title){
        this.type = type;
        this.name = name;
        this.title = title;
    }

    public Question() {

    }
    public Long getId() { return id; }
    public String getType() { return type; }
    public String getName() { return name; }
    public String getTitle() { return title; }

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
