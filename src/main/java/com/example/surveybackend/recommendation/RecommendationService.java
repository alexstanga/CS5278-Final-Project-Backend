package com.example.surveybackend.recommendation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendationService {

    private Map<String, List<Recommendation>> recommendationsMap;

    public RecommendationService(String fileName) throws IOException {
        loadRecommendations(fileName);
    }

    private void loadRecommendations(String fileName) throws IOException {
        // Load recommendations.json from classpath
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new FileNotFoundException("File not found: " + fileName);
        }

        // Parse the JSON file into a Map
        ObjectMapper mapper = new ObjectMapper();
        recommendationsMap = mapper.readValue(inputStream, new TypeReference<Map<String, List<Recommendation>>>() {});
    }

    public Map<String, String> getRecommendationsForCategories(Map<String, Integer> categoryScores) {
        Map<String, String> result = new HashMap<>();

        for (Map.Entry<String, Integer> entry : categoryScores.entrySet()) {
            String category = entry.getKey();
            Integer score = entry.getValue();

            // Fetch recommendations for the category
            List<Recommendation> recommendations = recommendationsMap.get(category);
            if (recommendations != null) {
                String recommendationText = getRecommendationForScore(recommendations, score);
                result.put(category, recommendationText);
            }
        }
        return result;
    }

    private String getRecommendationForScore(List<Recommendation> recommendations, int score) {
        recommendations.sort(Comparator.comparingInt(Recommendation::getScore));

        // Find the appropriate recommendation based on the score
        for (Recommendation recommendation : recommendations) {
            if (score <= recommendation.getScore()) {
                return recommendation.getRecommendation();
            }
        }
        return "No recommendation available.";
    }
}
