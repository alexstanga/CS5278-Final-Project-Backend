package com.example.surveybackend.recommendation;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.IOException;
import java.util.Map;

public class PdfGenerator {

    public void createPdf(String fileName, Map<String, String> recommendations) throws IOException {
        // Create a new document
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = null;

        // Start a new content stream
        try {
            contentStream = new PDPageContentStream(document, page);
            // Set font and size
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);

            // Write title
            contentStream.beginText();
            contentStream.newLineAtOffset(25, 750);
            contentStream.showText("Survey Results and Recommendations");
            contentStream.endText();

            // Write recommendations
            int yPosition = 700;
            for (Map.Entry<String, String> entry : recommendations.entrySet()) {
                if (yPosition < 50) {
                    contentStream.endText();
                    contentStream.close();
                    document.addPage(new PDPage());
                    contentStream = new PDPageContentStream(document, document.getPage(document.getNumberOfPages() - 1));
                    yPosition = 750;
                }

                contentStream.beginText();
                contentStream.newLineAtOffset(25, yPosition);
                contentStream.showText(entry.getKey() + ": " + entry.getValue());
                contentStream.endText();
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
}