package com.example.surveybackend.pdf;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;
import java.util.Map;

public class RecommendationsComponent implements PDFComponent{
    private Map<String, String> recommendations;

    public RecommendationsComponent(Map<String, String> recommendations) {
        this.recommendations = recommendations;
    }
    @Override
    public void draw(PDPageContentStream contentStream) throws IOException {

    }
}
