package com.example.surveybackend;

import com.example.surveybackend.jpa.ResultRepository;
import com.example.surveybackend.jpa.SurveyRepository;
import com.example.surveybackend.pdf.PdfGenerator;
import com.example.surveybackend.recommendation.RecommendationService;
import com.example.surveybackend.survey.Result;
import com.example.surveybackend.survey.ResultResponse;
import com.example.surveybackend.survey.SurveyJpaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class SurveyIntegrationApplicationTests {

    @InjectMocks
    private SurveyJpaService surveyJpaService;  // Service being tested

    @Mock
    private ResultRepository resultRepository;  // Mocked dependency

    @Mock
    private SurveyRepository surveyRepository;  // Mocked dependency

    @Mock
    private RecommendationService recommendationService;  // Mocked dependency

    @Mock
    private PdfGenerator pdfGenerator;  // Mocked dependency


    @BeforeEach
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.initMocks(this);
    }

    // Test 9. Test calculating the scores
    @Test
    public void testCalculateTotalScore() throws Exception {
        // Setup mock data and behavior
        Result result = new Result();
        result.setId(1);
        result.setResultResponses(Arrays.asList(
                new ResultResponse("phq-1", "answer", 1),
                new ResultResponse("phq-2", "answer", 2)
        ));

        when(resultRepository.findById(1)).thenReturn(Optional.of(result));


        // Call the method under test
        surveyJpaService.calculateTotalScore(1);

        Map<String, Integer> expectedCategoryScores = new HashMap<>();
        expectedCategoryScores.put("phq", 3); // phq-1 + phq-2 = 1 + 2

        verify(resultRepository).save(result); // Verify save interaction

        // Verify that the result's category scores are updated correctly
        assertEquals(expectedCategoryScores, result.getCategoryScores());
    }

    // Test 10. Test calculating the scores with all answers selected
    @Test
    public void testCalculateTotalScoreBoth() throws Exception {
        // Setup mock data and behavior
        Result result = new Result();
        result.setId(1);
        result.setResultResponses(Arrays.asList(
                new ResultResponse("phq-1", "answer", 1),
                new ResultResponse("phq-2", "answer", 2),
                new ResultResponse("phq-3", "answer", 1),
                new ResultResponse("phq-4", "answer", 2),
                new ResultResponse("phq-5", "answer", 1),
                new ResultResponse("phq-6", "answer", 2),
                new ResultResponse("phq-7", "answer", 1),
                new ResultResponse("gad-1", "answer", 2),
                new ResultResponse("gad-2", "answer", 1),
                new ResultResponse("gad-3", "answer", 2),
                new ResultResponse("gad-4", "answer", 2),
                new ResultResponse("gad-5", "answer", 1),
                new ResultResponse("gad-6", "answer", 2),
                new ResultResponse("gad-7", "answer", 2)
        ));

        when(resultRepository.findById(1)).thenReturn(Optional.of(result));


        // Call the method under test
        surveyJpaService.calculateTotalScore(1);

        Map<String, Integer> expectedCategoryScores = new HashMap<>();
        expectedCategoryScores.put("phq", 10);
        expectedCategoryScores.put("gad", 12);

        verify(resultRepository).save(result); // Verify save interaction

        // Verify that the result's category scores are updated correctly
        assertEquals(expectedCategoryScores, result.getCategoryScores());
    }


}
