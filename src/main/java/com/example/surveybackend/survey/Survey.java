package com.example.surveybackend.survey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

/**
 * Represents a survey entity in the system.
 * This class stores the details of a survey, including its name,
 * JSON representation, and associated results.
 */
@Entity(name="survey_details")
public class Survey {

    public Survey() {

    }

    @Id
    @GeneratedValue
    private Integer id;
    private String name;

    @Lob
    @Column( length = 100000 )
    private String json;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "survey")
    @JsonIgnore
    private List<Result> results;

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

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}