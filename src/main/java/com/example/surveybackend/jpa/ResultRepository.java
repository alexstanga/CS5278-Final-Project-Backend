package com.example.surveybackend.jpa;

import com.example.surveybackend.survey.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<Result, Integer> {
}
