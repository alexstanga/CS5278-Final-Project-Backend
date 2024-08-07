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

/**
 * Service class for handling recommendations based on survey scores.
 */
public class RecommendationService {

    private Map<String, List<Recommendation>> recommendationsMap;

    /**
     * Constructor that loads recommendations from a specified JSON file.
     *
     * @param fileName The name of the JSON file containing recommendations
     * @throws IOException If the file is not found or cannot be read
     */
    public RecommendationService(String fileName) throws IOException {
        loadRecommendations(fileName);
    }

    /**
     * Load recommendations from a JSON file into a map.
     *
     * @param fileName The name of the JSON file to load
     * @throws IOException If the file is not found or cannot be read
     */
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

    /**
     * Get recommendations for categories based on their scores.
     *
     * @param categoryScores Map of category names to their scores
     * @return Map of category names to their recommendations
     */
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

    /**
     * Find the appropriate recommendation based on the score.
     *
     * @param recommendations List of recommendations for a category
     * @param score The score for which to find a recommendation
     * @return The recommendation text
     */
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
