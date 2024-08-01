package com.example.surveybackend.survey;

import com.example.surveybackend.jpa.ResultRepository;
import com.example.surveybackend.jpa.ResultResponseRepository;
import com.example.surveybackend.jpa.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Service
public class SurveyJpaService {

    @Autowired
    private SurveyRepository surveyRepository;
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private ResultResponseRepository resultResponseRepository;

    public Result saveResult(Integer surveyId, Map<String, String> responses) {
        // Find the existing Survey by ID
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new SurveyNotFoundException("ID: " + surveyId));

        // Create a new Result
        Result result = new Result();
        result.setSurvey(survey);
        resultRepository.save(result);

        // Create ResultResponse entries
        List<ResultResponse> resultResponses = new ArrayList<>();
        for (Map.Entry<String, String> entry : responses.entrySet()) {
            ResultResponse resultResponse = new ResultResponse(entry.getKey(), entry.getValue());
            resultResponse.setResult(result);
            resultResponses.add(resultResponse);
        }

        // Save ResultResponses entries
        result.setResultResponses(resultResponses);
        resultResponseRepository.saveAll(resultResponses);

        return result;
    }
}
