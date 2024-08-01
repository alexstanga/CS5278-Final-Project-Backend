package com.example.surveybackend.survey;

import com.example.surveybackend.jpa.ResultRepository;
import com.example.surveybackend.jpa.ResultResponseRepository;
import com.example.surveybackend.jpa.SurveyRepository;
import com.example.surveybackend.question.Question;
import com.example.surveybackend.question.RadioGroupQuestion;
import com.example.surveybackend.question.TextQuestion;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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

    public static Survey survey1 = new Survey();
    public static List<Question> questions1 = new ArrayList<>();
    private static ObjectMapper objectMapper;

    {
        String json = " ";
        for (int i = 1; i <= 10; i++) {
            Question question;
            if (i%2 == 0) {
                question = new TextQuestion("question" + i, "Question" + i);
            } else {
                question = new RadioGroupQuestion(
                        "quetion" + i,
                        "Question" + i,
                        Arrays.asList("Option A", "Option B", "Option C")
                );
            }
            questions1.add(question);
        }
        try {
            json = objectMapper.writeValueAsString(questions1);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        survey1.setName("Survey1");
        survey1.setJson(json);
        surveyRepository.save(survey1);
    }

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

    //TODO
    public Survey createSurvey(){
        // Create new SurveyGroup with multiple questions

        // Convert SurveyGroup to Survey Object

        // return Survey Object
        return null;
    }
}
