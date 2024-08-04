package com.example.surveybackend.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecommendationsComponent implements PDFComponent{
    private Map<String, String> recommendations;

    private static final int PAGE_WIDTH = 500; // Maximum width for text
    private static final int MARGIN = 25; // Left margin
    private static final int LINE_HEIGHT = 15; // Height of each line
    private static final PDFont BOLD_FONT = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
    private static final PDFont FONT = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
    private static final float FONT_SIZE = 12;

    private float yPosition;

    public RecommendationsComponent(Map<String, String> recommendations) {
        this.recommendations = recommendations;
    }
    @Override
    public void draw(PDPageContentStream contentStream, PDFBuilder builder) throws IOException {
        yPosition = 680;
        contentStream.setFont(BOLD_FONT, FONT_SIZE);

        for (Map.Entry<String, String> entry : recommendations.entrySet()) {
            if (yPosition < 50) {
                contentStream.endText();
                contentStream.close();
                builder.addPage();
                contentStream = builder.getContentStream();
                yPosition = 750;
            }
            contentStream.setFont(BOLD_FONT, FONT_SIZE);
            contentStream.beginText();
            contentStream.newLineAtOffset(25, yPosition);
            contentStream.showText(entry.getKey());
            yPosition -= 20;
            contentStream.endText();
            writeWrappedText("    - " + entry.getValue(), 25, yPosition, contentStream, builder);
            yPosition -= 20;
        }
    }

    public void writeWrappedText(String text, float startX, float startY, PDPageContentStream contentStream, PDFBuilder builder)
            throws IOException {

        List<String> lines = wrapText(text, PAGE_WIDTH - 2 * MARGIN);
        contentStream.setFont(FONT, FONT_SIZE);
        yPosition = startY;
        for (String line : lines) {
            if (yPosition < 50) { // If at the bottom of the page
                contentStream.endText();
                contentStream.close();
                // Add new page and reset contentStream
                builder.addPage();
                contentStream = builder.getContentStream();
                yPosition = 750; // Reset Y position for new page
                contentStream.setFont(FONT, FONT_SIZE);
            }

            contentStream.beginText();
            contentStream.newLineAtOffset(startX, yPosition);
            contentStream.showText(line);
            contentStream.endText();
            yPosition -= LINE_HEIGHT;
        }
        yPosition -= LINE_HEIGHT * 2;
    }

    private List<String> wrapText(String text, float maxWidth) throws IOException {
        // Split text into lines based on maxWidth
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        List<String> lines = new ArrayList<>();

        for (String word : words) {
            String testLine = line.toString() + (line.length() > 0 ? " " : "") + word;
            if (BOLD_FONT.getStringWidth(testLine) / 1000 * FONT_SIZE > maxWidth) {
                lines.add(line.toString());
                line = new StringBuilder(word);
            } else {
                line.append(line.length() > 0 ? " " : "").append(word);
            }
        }
        lines.add(line.toString());

        return lines;
    }
}
