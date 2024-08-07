package com.example.surveybackend.survey;

import com.example.surveybackend.survey.Survey;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the result of a survey.
 * This entity stores information about a survey result, including the survey reference,
 * result responses, JSON representation of the responses, and category scores.
 */
@Entity
public class Result {

    public Result() {

    }

    @Id
    @GeneratedValue
    private Integer id;

    // Many-to-one relationship with Survey entity
    // The survey this result is associated with
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    @JsonIgnore
    private Survey survey;

    // One-to-many relationship with ResultResponse entity
    // List of responses associated with this result
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "result")
    private List<ResultResponse> resultResponses;
    @Lob
    @Column( length = 100000 )
    private String json;

    @ElementCollection
    @MapKeyColumn(name = "category")
    @Column(name = "total_score")
    @CollectionTable(name = "result_category_scores", joinColumns = @JoinColumn(name = "result_id"))
    private Map<String, Integer> categoryScores = new HashMap<>();

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

    public List<ResultResponse> getResultResponses() {
        return resultResponses;
    }

    public void setResultResponses(List<ResultResponse> resultResponses) {
        this.resultResponses = resultResponses;
    }

    public Map<String, Integer> getCategoryScores() {
        return categoryScores;
    }

    public void setCategoryScores(Map<String, Integer> categoryScores) {
        this.categoryScores = categoryScores;
    }
}
