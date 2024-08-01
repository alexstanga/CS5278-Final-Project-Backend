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
    @JoinColumn(name = "survey_id")
    private Integer survey;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "result")
    private List<ResultResponse> resultResponses;
    @Lob
    @Column( length = 100000 )
    private String json;

    public Result(Integer id, Integer survey, String json) {
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

    public Integer getSurvey() {
        return survey;
    }

    public void setSurvey(Integer survey) {
        this.survey = survey;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public List<ResultResponse> getResultResponses() {
        return resultResponses;
    }

    public void setResultResponses(List<ResultResponse> resultResponses) {
        this.resultResponses = resultResponses;
    }
}
