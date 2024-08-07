package com.example.surveybackend.pdf;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a composite PDF component that can contain multiple other PDF components.
 * Implements the PDFComponent interface to draw all contained components onto the PDF.
 */
public class CompositePDFComponent implements PDFComponent {
    private List<PDFComponent> components = new ArrayList<>();

    /**
     * Adds a new PDFComponent to this composite component.
     *
     * @param component the PDFComponent to be added
     */
    public void addComponent(PDFComponent component) {
        components.add(component);
    }

    /**
     * Draws all contained PDFComponents onto the given PDPageContentStream.
     *
     * @param contentStream the content stream to draw onto
     * @param builder the PDFBuilder used for drawing
     * @throws IOException if an error occurs while drawing
     */
    @Override
    public void draw(PDPageContentStream contentStream, PDFBuilder builder) throws IOException {
        for (PDFComponent component : components) {
            component.draw(contentStream, builder);
        }
    }
}
