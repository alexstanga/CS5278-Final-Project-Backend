package com.example.surveybackend.survey;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class SurveyDaoService {

    private static List<Survey> surveys = new ArrayList<>();
    private static int surveyCount = 0;

    public List<Survey> findAll() {
        return surveys;
    }

    public Survey save(Survey survey){
        survey.setId(++surveyCount);
        surveys.add(survey);
        return survey;
    }

    public Survey findOne(int id){
        Predicate<? super Survey> predicate = survey -> survey.getId().equals(id);
        return surveys.stream().filter(predicate).findFirst().orElse(null);
    }

    public void deleteById(int id) {
        Predicate<? super Survey> predicate = survey -> survey.getId().equals(id);
        surveys.removeIf(predicate);
    }
}
