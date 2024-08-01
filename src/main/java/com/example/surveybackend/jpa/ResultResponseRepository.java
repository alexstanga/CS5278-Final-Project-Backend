package com.example.surveybackend.jpa;

import com.example.surveybackend.survey.ResultResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultResponseRepository extends JpaRepository<ResultResponse, Integer> {
}
