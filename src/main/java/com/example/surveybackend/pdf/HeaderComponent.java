package com.example.surveybackend.pdf;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.IOException;
/**
 * Represents a header component in a PDF document.
 * Implements the PDFComponent interface to draw the header text onto the PDF.
 */
public class HeaderComponent implements PDFComponent{
    private static final PDFont BOLD_FONT = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
    private String title;

    /**
     * Constructs a HeaderComponent with the specified title.
     *
     * @param title the title text to be displayed in the header
     */
    public HeaderComponent(String title) {
        this.title = title;
    }

    /**
     * Draws the header title onto the given PDPageContentStream.
     *
     * @param contentStream the content stream to draw onto
     * @param builder the PDFBuilder used for additional drawing configuration
     * @throws IOException if an error occurs while drawing
     */
    @Override
    public void draw(PDPageContentStream contentStream, PDFBuilder builder) throws IOException {
        contentStream.setFont(BOLD_FONT, 16);
        contentStream.beginText();
        contentStream.newLineAtOffset(25, 750);
        contentStream.showText(title);
        contentStream.endText();
    }
}
