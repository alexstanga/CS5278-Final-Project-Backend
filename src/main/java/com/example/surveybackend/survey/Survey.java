package com.example.surveybackend.survey;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name="survey_details")
public class Survey {

    protected Survey() {

    }

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String json;

    public Survey(Integer id, String name, String json) {
        this.id = id;
        this.name = name;
        this.json = json;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}