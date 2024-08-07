package com.example.surveybackend.survey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

/**
 * Represents a response to a single question in a survey result.
 * This entity stores information about a specific response, including
 * the associated result, question, response text, and score.
 */
@Entity
public class ResultResponse {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "result_id")
    @JsonIgnore
    private Result result;

    private String question;
    private String response;

    private int score;

    public ResultResponse(String question, String response, int score) {
        this.question = question;
        this.response = response;
        this.score = score;
    }

    public ResultResponse() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getScore() { return score; }

    public void setScore(int score) { this.score = score; }
}
