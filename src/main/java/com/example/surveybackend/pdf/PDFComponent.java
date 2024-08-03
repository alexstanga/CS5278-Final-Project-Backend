package com.example.surveybackend.pdf;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;

// To generate a PDF report, we are going to use the Builder and Composite Pattern
public interface PDFComponent {
    void draw(PDPageContentStream contentStream) throws IOException;
}
