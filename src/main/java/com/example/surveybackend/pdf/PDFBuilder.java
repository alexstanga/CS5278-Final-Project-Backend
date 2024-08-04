package com.example.surveybackend.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PDFBuilder {
    private CompositePDFComponent compositeComponent = new CompositePDFComponent();
    private final PDDocument document;
    private final List<PDPage> pages = new ArrayList<>();
    private PDPageContentStream contentStream;

    public PDFBuilder(PDDocument document) {
        this.document = document;
    }

    public PDFBuilder addHeader(String title) {
        compositeComponent.addComponent(new HeaderComponent(title));
        return this;
    }

    public PDFBuilder addStoplightChart(int score) {
        compositeComponent.addComponent(new StopLightChartComponent(score));
        return this;
    }

    public PDFBuilder addRecommendations(Map<String, String> recommendations) {
        compositeComponent.addComponent(new RecommendationsComponent(recommendations));
        return this;
    }

    public PDFBuilder addPage() throws IOException {
        if (contentStream != null) {
            contentStream.close();
        }
        PDPage newPage = new PDPage();
        document.addPage(newPage);
        pages.add(newPage);
        contentStream = new PDPageContentStream(document, newPage);
        return this;
    }

    public PDPageContentStream getContentStream() {
        return contentStream;
    }

    public PDFBuilder startContentStream() throws IOException {
        if (pages.isEmpty()) {
            addPage(); // Ensure at least one page exists
        }
        return this;
    }

    public PDFBuilder finishContentStream() throws IOException {
        if (contentStream != null) {
            contentStream.close();
        }
        return this;
    }

    public void build() throws IOException {
        finishContentStream();
    }

    public void createPdf(String fileName) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            compositeComponent.draw(contentStream, this);
        }

        document.save(fileName);
        document.close();
    }
}
