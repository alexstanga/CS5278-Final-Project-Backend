package com.example.surveybackend.jpa;

import com.example.surveybackend.survey.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, Integer> {

}
