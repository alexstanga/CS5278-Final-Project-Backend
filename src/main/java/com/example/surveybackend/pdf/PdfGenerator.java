package com.example.surveybackend.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PdfGenerator {

    private static final int PAGE_WIDTH = 500; // Maximum width for text
    private static final int MARGIN = 25; // Left margin
    private static final int LINE_HEIGHT = 15; // Height of each line
    private static final PDFont BOLD_FONT = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
    private static final PDFont FONT = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
    private static final float FONT_SIZE = 12;


    public void createPdf(String fileName, Map<String, String> recommendations, int score) throws IOException {
        PDDocument document = new PDDocument();
        try {
            PDFBuilder builder = new PDFBuilder(document);
            builder.addHeader("Survey Results and Recommendations")
                    .addStoplightChart(score)
                    .addRecommendations(recommendations)
                    .createPdf(fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}