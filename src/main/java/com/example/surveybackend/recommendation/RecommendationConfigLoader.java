package com.example.surveybackend.recommendation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendationConfigLoader {
    private Map<String, List<Recommendation>> recommendations;

    public RecommendationConfigLoader(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        this.recommendations = objectMapper.readValue(new File(filePath),
                new TypeReference<Map<String, List<Recommendation>>>() {});
    }

    public List<Recommendation> getRecommendations(String category) {
        return recommendations.get(category);
    }
}
