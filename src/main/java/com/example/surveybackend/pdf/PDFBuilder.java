package com.example.surveybackend.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Builder class for creating a PDF document using Apache PDFBox.
 * Manages the addition of various PDF components and handles the creation and management of PDF pages and content streams.
 */
public class PDFBuilder {

    private CompositePDFComponent compositeComponent = new CompositePDFComponent();
    private final PDDocument document;
    private final List<PDPage> pages = new ArrayList<>();
    private PDPageContentStream contentStream;

    /**
     * Constructs a PDFBuilder with the given PDDocument.
     *
     * @param document the PDF document to build
     */
    public PDFBuilder(PDDocument document) {
        this.document = document;
    }


    /**
     * Adds a header component with the specified title to the PDF.
     *
     * @param title the title text for the header
     * @return the PDFBuilder instance for method chaining
     */

    public PDFBuilder addHeader(String title) {
        compositeComponent.addComponent(new HeaderComponent(title));
        return this;
    }

    /**
     * Adds a stoplight chart component with the given score to the PDF.
     *
     * @param score the score for the stoplight chart
     * @return the PDFBuilder instance for method chaining
     */
    public PDFBuilder addStoplightChart(int score) {
        compositeComponent.addComponent(new StopLightChartComponent(score));
        return this;
    }

    /**
     * Adds a recommendations component with the specified recommendations to the PDF.
     *
     * @param recommendations a map of recommendations to be included in the PDF
     * @return the PDFBuilder instance for method chaining
     */
    public PDFBuilder addRecommendations(Map<String, String> recommendations) {
        compositeComponent.addComponent(new RecommendationsComponent(recommendations));
        return this;
    }

    /**
     * Adds a new page to the PDF document and initializes a new content stream.
     *
     * @return the PDFBuilder instance for method chaining
     * @throws IOException if an error occurs while creating or closing the content stream
     */
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

    /**
     * Gets the current content stream.
     *
     * @return the current content stream
     */
    public PDPageContentStream getContentStream() {
        return contentStream;
    }

    /**
     * Starts a new content stream for drawing on the PDF.
     *
     * @return the PDFBuilder instance for method chaining
     * @throws IOException if an error occurs while starting the content stream
     */
    public PDFBuilder startContentStream() throws IOException {
        if (pages.isEmpty()) {
            addPage(); // Ensure at least one page exists
        }
        return this;
    }

    /**
     * Closes the current content stream.
     *
     * @return the PDFBuilder instance for method chaining
     * @throws IOException if an error occurs while closing the content stream
     */
    public PDFBuilder finishContentStream() throws IOException {
        if (contentStream != null) {
            contentStream.close();
        }
        return this;
    }

    /**
     * Finalizes the PDF building process by closing the content stream.
     *
     * @throws IOException if an error occurs while closing the content stream
     */
    public void build() throws IOException {
        finishContentStream();
    }

    /**
     * Creates a PDF file with the specified filename and saves it.
     *
     * @param fileName the name of the file to save the PDF as
     * @throws IOException if an error occurs while creating or saving the PDF
     */
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
