package com.example.surveybackend.pdf;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompositePDFComponent implements PDFComponent {
    private List<PDFComponent> components = new ArrayList<>();

    public void addComponent(PDFComponent component) {
        components.add(component);
    }
    @Override
    public void draw(PDPageContentStream contentStream, PDFBuilder builder) throws IOException {
        for (PDFComponent component : components) {
            component.draw(contentStream, builder);
        }
    }
}
