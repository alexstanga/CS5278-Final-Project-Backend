package com.example.surveybackend.pdf;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;

// To generate a PDF report, we are going to use the Builder and Composite Pattern
/**
 * Interface representing a component that can be drawn on a PDF document.
 * Implementing classes should provide specific drawing behavior for different types of PDF components.
 */
public interface PDFComponent {

    /**
     * Draws the component on the given PDF content stream.
     *
     * @param contentStream the content stream used for drawing on the PDF page
     * @param builder the PDFBuilder instance managing the PDF document and pages
     * @throws IOException if an error occurs while drawing on the PDF
     */
    void draw(PDPageContentStream contentStream, PDFBuilder builder) throws IOException;
}
