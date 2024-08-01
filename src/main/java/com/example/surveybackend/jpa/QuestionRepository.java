package com.example.surveybackend.jpa;

import com.example.surveybackend.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
