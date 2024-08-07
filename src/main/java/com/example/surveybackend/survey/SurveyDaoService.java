package com.example.surveybackend.survey;

import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Service class for managing surveys.
 * This class provides CRUD operations for in-memory storage of surveys.
 */
@Component
public class SurveyDaoService {

    private static List<Survey> surveys = new ArrayList<>();
    private static int surveyCount = 1;

    static Survey survey1 = new Survey(
            1,
            "Product Feedback Survey",
            "{\"pages\": [{\"elements\": [{\"type\": \"matrix\",\"name\": \"Quality\",\"title\": \"Please indicate if you agree or disagree with the following statements\",\"columns\": [{\"value\": 1,\"text\": \"Strongly disagree\"},{\"value\": 2,\"text\": \"Disagree\"},{\"value\": 3,\"text\": \"Neutral\"},{\"value\": 4,\"text\": \"Agree\"},{\"value\": 5,\"text\": \"Strongly agree\"}],\"rows\": [{\"value\": \"affordable\",\"text\": \"Product is affordable\"},{\"value\": \"does what it claims\",\"text\": \"Product does what it claims\"},{\"value\": \"better then others\",\"text\": \"Product is better than other products on the market\"},{\"value\": \"easy to use\",\"text\": \"Product is easy to use\"}]}]}]}"
    );

    static {
        surveys.add(survey1);
    }

    /**
     * Returns the list of all surveys.
     *
     * @return List of all surveys
     */
    public List<Survey> findAll() {
        return surveys;
    }

    /**
     * Saves a new survey or updates an existing one.
     * Assigns a new ID to the survey if it is new.
     *
     * @param survey Survey object to be saved
     * @return The saved Survey object with assigned ID
     */
    public Survey save(Survey survey){
        survey.setId(++surveyCount);
        surveys.add(survey);
        return survey;
    }

    /**
     * Finds a survey by its ID.
     *
     * @param id ID of the survey to be found
     * @return The found Survey object or null if not found
     */
    public Survey findOne(int id){
        Predicate<? super Survey> predicate = survey -> survey.getId().equals(id);
        return surveys.stream().filter(predicate).findFirst().orElse(null);
    }

    /**
     * Deletes a survey by its ID.
     *
     * @param id ID of the survey to be deleted
     */
    public void deleteById(int id) {
        Predicate<? super Survey> predicate = survey -> survey.getId().equals(id);
        surveys.removeIf(predicate);
    }


}
