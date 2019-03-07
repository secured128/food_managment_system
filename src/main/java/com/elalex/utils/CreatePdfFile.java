package com.elalex.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class CreatePdfFile {
    public static void createPdfFile() throws IOException {
        System.out.println("Inside createPdfFile");

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        contentStream.setFont(PDType1Font.COURIER, 12);
        contentStream.beginText();
        contentStream.showText("Hello World");
        contentStream.endText();
        contentStream.close();

        document.save("pdfBoxHelloWorld.pdf");
        document.close();
        File file = new File("pdfBoxHelloWorld.pdf");
        if (file.exists()) {
            System.out.println("PATH: " + file.getPath());

            // Convert file to URI
            URI uri = file.toURI();
            System.out.println("URI: " + uri.toString());

            // Convert URI to URL
            URL url;
            try {
                url = uri.toURL();
                System.out.println("URL: " + url.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }
    }
}
