package com.example.surveybackend.survey;

import com.example.surveybackend.jpa.ResultRepository;
import com.example.surveybackend.jpa.ResultResponseRepository;
import com.example.surveybackend.jpa.SurveyRepository;
import com.example.surveybackend.question.Choice;
import com.example.surveybackend.question.Question;
import com.example.surveybackend.question.RadioGroupQuestion;
import com.example.surveybackend.question.TextQuestion;
import com.example.surveybackend.pdf.PdfGenerator;
import com.example.surveybackend.recommendation.RecommendationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class SurveyJpaService {

    @Autowired
    private SurveyRepository surveyRepository;
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private ResultResponseRepository resultResponseRepository;

    private RecommendationService recommendationService;

    public void init() {
        List<Question> questions1 = new ArrayList<>();
        String json = " ";
        for (int i = 1; i <= 10; i++) {
            Question question;
            if (i%2 == 0) {
                question = new TextQuestion("question" + i, "Question" + i);
            } else {
                question = new RadioGroupQuestion(
                        "quetion" + i,
                        "Question" + i,
                        Arrays.asList(new Choice("Option A", 1),
                                new Choice("Option B", 2),
                                new Choice("Option C", 3))
                );
            }
            questions1.add(question);
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            json = objectMapper.writeValueAsString(questions1);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Survey survey1 = new Survey(1, "Survey1", json);
        surveyRepository.save(survey1);
    }

    public Result saveResult(Integer surveyId, Map<String, Map<String, ResultResponseDto>> responses) {
        // Find the existing Survey by ID
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new SurveyNotFoundException("ID: " + surveyId));

        // Create a new Result
        Result result = new Result();
        result.setSurvey(survey);
        resultRepository.save(result);

        // Create ResultResponse entries
        List<ResultResponse> resultResponses = new ArrayList<>();
        for (Map.Entry<String, Map<String, ResultResponseDto>> entry : responses.entrySet()) {
            String questionType = entry.getKey();
            Map<String, ResultResponseDto> questions = entry.getValue();

            for (Map.Entry<String, ResultResponseDto> questionEntry : questions.entrySet()) {
                String questionName = questionEntry.getKey();
                ResultResponseDto resultResponseDto = questionEntry.getValue();

                ResultResponse resultResponse = new ResultResponse();
                resultResponse.setQuestion(questionName);
                resultResponse.setResponse(resultResponseDto.getAnswer());
                resultResponse.setScore(resultResponseDto.getScore());
                resultResponse.setResult(result);

                resultResponses.add(resultResponse);
            }


        }

        // Save ResultResponses entries;
        resultResponseRepository.saveAll(resultResponses);

        // Associate ResultResponses with Result
        result.setResultResponses(resultResponses);
        resultRepository.save(result);

        return result;
    }

    // Calculate the total scores for each survey section.
    public void calculateTotalScore(Integer resultId) throws IOException {
        // Initialize a map to hold the total scores for each category
        Map<String, Integer> categoryScores = new HashMap<>();
        // Find the existing ResultResponses by ID
        Result result = resultRepository.findById(resultId)
                .orElseThrow(() -> new SurveyNotFoundException("ID: " + resultId));
        for (ResultResponse response : result.getResultResponses()){
            String questionId = response.getQuestion();
            String category = questionId.substring(0, questionId.lastIndexOf('-'));

            // Get the score for the current response
            int score = response.getScore();

            // Update the category score in the map
            categoryScores.merge(category, score, Integer::sum);
        }

        result.setCategoryScores(categoryScores);
        resultRepository.save(result);

        System.out.println("end");

        this.recommendationService = new RecommendationService("recommendations.json");
        Map<String, String> recommendations = recommendationService.getRecommendationsForCategories(categoryScores);


        PdfGenerator pdfGenerator = new PdfGenerator();
        pdfGenerator.createPdf("Survey_Results_" + resultId + ".pdf", recommendations);
    }

    //TODO
    public Survey createSurvey(){
        // Create new SurveyGroup with multiple questions

        // Convert SurveyGroup to Survey Object

        // return Survey Object
        return null;
    }
}
