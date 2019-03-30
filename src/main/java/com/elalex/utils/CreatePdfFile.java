package com.elalex.utils;

import com.elalex.food.model.SelectRecipeInstructionsDB;
import com.elalex.food.model.SelectRecipeQueryDB;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

public class CreatePdfFile {
    public static String createPdfFile(List<SelectRecipeQueryDB> selectRecipeQueryDBList, List <SelectRecipeInstructionsDB> selectRecipeInstructionsDBList, String recipeName) throws IOException {
        System.out.println("Inside createPdfFile");
        Document document = new Document();
        String fileName = "recipe"+GeneralUtils.getDateTime()+".pdf";
        try
        {

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));

            String path = "/home/alexela/Downloads/arialuni.ttf";
            FontFactory.register(path);
            BaseFont bf = BaseFont.createFont(path, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            PdfPTable table = new PdfPTable(1);
            Font font = new Font(bf, 20);
            Phrase phrase = new Phrase(recipeName, font);
            PdfPCell cell = new PdfPCell(phrase);
            addCellToPdf ( table,  cell,  phrase);
            addProductsToPdf ( table, cell,  phrase,  font,  bf,  selectRecipeQueryDBList);
            addInstructionsToPdf( table,  cell,  phrase,  font,  bf,  selectRecipeInstructionsDBList);
          /*  font = new Font(bf, 15);
            phrase = new Phrase(
                    "חומרים", font);
            addCellToPdf ( table,  cell,  phrase);
            font = new Font(bf, 10);
            Iterator<SelectRecipeQueryDB> selectRecipeQueryDBIterator= selectRecipeQueryDBList.iterator();
            while( selectRecipeQueryDBIterator.hasNext())
            {
               SelectRecipeQueryDB selectRecipeQueryDBLine= selectRecipeQueryDBIterator.next();

               phrase = new Phrase(
                       GeneralUtils.padRight(selectRecipeQueryDBLine.getRecipeDescriptionName(),25)+" :  "
                               +GeneralUtils.padRight(Double.toString(selectRecipeQueryDBLine.getCalculatedQuantity()),10) +"  "+selectRecipeQueryDBLine.getUnit(), font);
                addCellToPdf ( table,  cell,  phrase);
            }
            phrase = new Phrase("", font);
            addCellToPdf ( table,  cell,  phrase);*/
            document.open();
            document.add(table);
           // Paragraph
          //  document.ssetRunDirection(PdfWriter.RUN_DIRECTION_NO_BIDI)
            document.close();
            writer.close();
        } catch (DocumentException e)
        {
            e.printStackTrace();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return fileName;


    }

    public static void addCellToPdf (PdfPTable table, PdfPCell cell, Phrase phrase) throws IOException
    {
        cell = new PdfPCell(phrase);
        cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        cell.setBorderColor(BaseColor.WHITE);
        table.addCell(cell);
    }

    public static void addProductsToPdf (PdfPTable table, PdfPCell cell, Phrase phrase, Font font, BaseFont bf, List<SelectRecipeQueryDB> selectRecipeQueryDBList) throws IOException
    {
        font = new Font(bf, 15);
        phrase = new Phrase(
                "חומרים", font);
        addCellToPdf ( table,  cell,  phrase);
        font = new Font(bf, 10);
        Iterator<SelectRecipeQueryDB> selectRecipeQueryDBIterator= selectRecipeQueryDBList.iterator();
        while( selectRecipeQueryDBIterator.hasNext())
        {
            SelectRecipeQueryDB selectRecipeQueryDBLine= selectRecipeQueryDBIterator.next();

            phrase = new Phrase(
                    GeneralUtils.padRight(selectRecipeQueryDBLine.getRecipeDescriptionName(),25)+" :  "
                            +GeneralUtils.padRight(Double.toString(selectRecipeQueryDBLine.getCalculatedQuantity()),10) +"  "+selectRecipeQueryDBLine.getUnit(), font);
            addCellToPdf ( table,  cell,  phrase);
        }
        phrase = new Phrase("", font);
        addCellToPdf ( table,  cell,  phrase);
    }

    public static void addInstructionsToPdf (PdfPTable table, PdfPCell cell, Phrase phrase, Font font, BaseFont bf, List <SelectRecipeInstructionsDB> selectRecipeInstructionsDBList) throws IOException
    {
        font = new Font(bf, 15);
        phrase = new Phrase("הוראות", font);
        addCellToPdf ( table,  cell,  phrase);
        font = new Font(bf, 10);
        Iterator<SelectRecipeInstructionsDB> selectRecipeInstructionsDBIterator= selectRecipeInstructionsDBList.iterator();
        while( selectRecipeInstructionsDBIterator.hasNext())
        {
            SelectRecipeInstructionsDB selectRecipeInstructionsDBLine= selectRecipeInstructionsDBIterator.next();

            phrase = new Phrase(
                    GeneralUtils.padRight(Integer.toString(selectRecipeInstructionsDBLine.getInstructionOrder()),5)+"   "
                            +GeneralUtils.padRight(selectRecipeInstructionsDBLine.getInstructionDescription(),20), font);
            addCellToPdf ( table,  cell,  phrase);
        }
        phrase = new Phrase("", font);
        addCellToPdf ( table,  cell,  phrase);
    }
}
