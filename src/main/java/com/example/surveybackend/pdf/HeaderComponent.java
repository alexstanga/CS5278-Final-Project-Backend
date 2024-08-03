package com.example.surveybackend.pdf;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.IOException;

public class HeaderComponent implements PDFComponent{
    private static final PDFont BOLD_FONT = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
    private String title;


    public HeaderComponent(String title) {
        this.title = title;
    }
    @Override
    public void draw(PDPageContentStream contentStream) throws IOException {
        contentStream.setFont(BOLD_FONT, 16);
        contentStream.beginText();
        contentStream.newLineAtOffset(25, 750);
        contentStream.showText(title);
        contentStream.endText();
    }
}
