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

    private PDDocument document;
    private PDPageContentStream contentStream;
    private float yPosition;

    public void createPdf(String fileName, Map<String, String> recommendations) throws IOException {
        // Create a new document
        document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        contentStream = null;

        // Start a new content stream
        try {
            contentStream = new PDPageContentStream(document, page);
            // Set font and size
            contentStream.setFont(BOLD_FONT, FONT_SIZE);

            // Write title
            contentStream.beginText();
            contentStream.newLineAtOffset(25, 750);
            contentStream.showText("Survey Results and Recommendations");
            contentStream.endText();

            // Write recommendations
            yPosition = 700;
            for (Map.Entry<String, String> entry : recommendations.entrySet()) {
                if (yPosition < 50) {
                    contentStream.endText();
                    contentStream.close();
                    document.addPage(new PDPage());
                    contentStream = new PDPageContentStream(document, document.getPage(document.getNumberOfPages() - 1));
                    yPosition = 750;
                }
                contentStream.setFont(BOLD_FONT, FONT_SIZE);
                contentStream.beginText();
                contentStream.newLineAtOffset(25, yPosition);
                contentStream.showText(entry.getKey());
                yPosition -= 20;
                contentStream.endText();
                writeWrappedText("    - " + entry.getValue(), 25, yPosition);
                yPosition -= 20;
            }
        } finally {
            if (contentStream != null) {
                contentStream.close();
            }
            // Save the document
            document.save(fileName);
            document.close();
        }

    }

    public void writeWrappedText(String text, float startX, float startY)
        throws IOException {

        List<String> lines = wrapText(text, PAGE_WIDTH - 2 * MARGIN);
        contentStream.setFont(FONT, FONT_SIZE);
        yPosition = startY;
        for (String line : lines) {
            if (yPosition < 50) { // If at the bottom of the page
                contentStream.endText();
                contentStream.close();
                // Add new page and reset contentStream
                document.addPage(new PDPage());
                contentStream = new PDPageContentStream(document, document.getPage(document.getNumberOfPages() - 1));
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