package com.example.surveybackend.survey;

import com.example.surveybackend.survey.Survey;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Result {

    protected Result() {

    }

    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Survey survey;
    @Lob
    @Column( length = 100000 )
    private String json;

    public Result(Integer id, Survey survey, String json) {
        this.id = id;
        this.survey = survey;
        this.json = json;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
