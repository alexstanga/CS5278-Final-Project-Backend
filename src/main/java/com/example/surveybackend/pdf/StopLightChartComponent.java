package com.example.surveybackend.pdf;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.awt.*;
import java.io.IOException;

public class StopLightChartComponent implements PDFComponent {
    private static final PDFont FONT = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
    private static final float CHART_RADIUS = 10; // Radius of stoplight circles
    private static final float CHART_X = 100; // X position of the chart
    private static final float CHART_Y = 730; // Y position of the chart
    private static final float FONT_SIZE = 12;
    private int overallScore;

    public StopLightChartComponent(int overallScore) {
        this.overallScore = overallScore;
    }
    @Override
    public void draw(PDPageContentStream contentStream) throws IOException {
        Color red = new Color(255, 0, 0);
        Color yellow = new Color(255, 255, 0);
        Color green = new Color(0, 255, 0);

        // Draw circles for stoplight chart
        contentStream.setNonStrokingColor(red);
        contentStream.addRect(CHART_X, CHART_Y, CHART_RADIUS, CHART_RADIUS);
        contentStream.fill();

        contentStream.setNonStrokingColor(yellow);
        contentStream.addRect(CHART_X, CHART_Y - CHART_RADIUS - 5, CHART_RADIUS, CHART_RADIUS);
        contentStream.fill();

        contentStream.setNonStrokingColor(green);
        contentStream.addRect(CHART_X, CHART_Y - 2 * (CHART_RADIUS + 5), CHART_RADIUS, CHART_RADIUS);
        contentStream.fill();

        // Draw text for the stoplight chart
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.setFont(FONT, FONT_SIZE);
        contentStream.beginText();
        contentStream.newLineAtOffset(CHART_X + CHART_RADIUS + 5, CHART_Y);
        contentStream.showText("High");
        contentStream.newLineAtOffset(CHART_X + CHART_RADIUS + 5, CHART_Y - CHART_RADIUS - 5);
        contentStream.showText("Medium");
        contentStream.newLineAtOffset(CHART_X + CHART_RADIUS + 5, CHART_Y - 2 * (CHART_RADIUS + 5));
        contentStream.showText("Low");
        contentStream.newLineAtOffset(CHART_X + CHART_RADIUS + 25, CHART_Y);
        contentStream.showText("Score: " + overallScore);
        contentStream.endText();
    }
}
