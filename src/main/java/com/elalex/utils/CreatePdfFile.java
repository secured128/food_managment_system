package com.elalex.utils;

import com.elalex.Application;
import com.elalex.food.model.ProductsPlacement;
import com.elalex.food.model.RecipeDescriptionDB;
import com.elalex.food.model.SelectRecipeInstructionsDB;
import com.elalex.food.model.SelectRecipeQueryDB;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static com.elalex.Application.bf;
import static com.elalex.Application.recipesPath;

public class CreatePdfFile {
    final static char TITLE_YES = 'Y';
    final static char TITLE_NO = 'N';
    public static String createPdfFile(List<SelectRecipeQueryDB> selectRecipeQueryDBList, List <SelectRecipeInstructionsDB> selectRecipeInstructionsDBList,
                                       String recipeName, HashMap<Long,SelectRecipeQueryDB> productHashMap,
                                       HashMap<Long, ProductsPlacement> productsPlacementHashMap,
                                       RecipeDescriptionDB recipeDescriptionDB, double quantity) throws IOException {
        System.out.println("Inside createPdfFile");
        TestMemory.testMem();
        Document document = new Document();
        String fileName = "recipe"+GeneralUtils.getDateTime()+".pdf";
        try
        {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));

            document.open();
            addRecipeHeaderToPdf ( bf, recipeName,  recipeDescriptionDB,  quantity, document);

            addProductsToPdf ( bf,  selectRecipeQueryDBList, document);
            productHashMap.entrySet().removeIf(productMap->
                (productMap.getValue().getCalculatedQuantity().equals(0d)));

            if (productHashMap.isEmpty())
            {
                addProductsPlacement(  bf, productsPlacementHashMap, document);
            }
            else
            {
                addMissingProductsToPdf( bf, productHashMap, document);
            }

            addInstructionsToPdf(  bf,  selectRecipeInstructionsDBList, document) ;


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

    public static void addCellToPdf (PdfPTable table,  Phrase phrase, char isTitle) throws IOException
    {
        PdfPCell cell = new PdfPCell(phrase);
        cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        if (isTitle==TITLE_YES)
        {
            cell.setBorderColor(BaseColor.WHITE);
        }
        else
        {
            cell.setBorderColor(BaseColor.WHITE);
        }
        table.addCell(cell);
    }

    public static void addRecipeHeaderToPdf ( BaseFont bf,
                                              String recipeName, RecipeDescriptionDB recipeDescriptionDB, double quantity,
                                          Document document) throws IOException, DocumentException
    {
        PdfPTable table = new PdfPTable(2);
        PdfPTable headerTable = new PdfPTable(1);

        Font font = new Font(bf, 20);
        headerTable.setHorizontalAlignment(Element.ALIGN_LEFT) ;
        table.setHorizontalAlignment(Element.ALIGN_RIGHT) ;
        byte [] image = recipeDescriptionDB.getImage();


        Phrase phrase = new Phrase(recipeName , font);
        addCellToPdf ( headerTable,  phrase, TITLE_YES);
        PdfPCell blankCell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        blankCell.setBorderColor( BaseColor.WHITE);
        headerTable.addCell(blankCell);
        document.add(headerTable);


        if (image!= null)
        {
            Image imgForPdf = Image.getInstance(image);
            imgForPdf.scalePercent(80f);
            PdfPCell  cell = new PdfPCell(imgForPdf);
            cell.setBorderColor(BaseColor.WHITE);
            table.addCell(cell);

        }
        else
        {
            table.addCell(blankCell);
        }

        font = new Font(bf, 16);
        phrase = new Phrase( recipeDescriptionDB.getDescription() + "\n" +
                Double.toString(quantity)+"  "+ recipeDescriptionDB.getUnit() , font);

        addCellToPdf ( table,  phrase, TITLE_YES);
        document.add(table);

    }

    public static void addProductsToPdf ( BaseFont bf,
                                         List<SelectRecipeQueryDB> selectRecipeQueryDBList,
                                         Document document) throws IOException, DocumentException
    {
        PdfPTable table = new PdfPTable(3);
        PdfPTable titleTable = new PdfPTable(1);
        titleTable.setHorizontalAlignment(Element.ALIGN_RIGHT) ;
        table.setHorizontalAlignment(Element.ALIGN_RIGHT) ;
        titleTable.setWidthPercentage(40);
        table.setWidthPercentage(40);

        float[] columnWidths = new float[]{10f, 10f, 30f};
        PdfPCell blankCell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        blankCell.setBorderColor( BaseColor.WHITE);
        titleTable.addCell(blankCell);
        Font font = new Font(bf, 15);
        Phrase phrase = new Phrase(
                "חומרים", font);
        addCellToPdf ( titleTable,  phrase, TITLE_YES);
        titleTable.addCell(blankCell);
        font = new Font(bf, 13);
        phrase = new Phrase(
                "יחידות", font);
        addCellToPdf ( table,  phrase, TITLE_NO);
        phrase = new Phrase(
               "כמות", font);
        addCellToPdf ( table, phrase, TITLE_NO);
        phrase = new Phrase(
                "חומר", font);
        addCellToPdf ( table, phrase, TITLE_NO);
        font = new Font(bf, 10);

        Iterator<SelectRecipeQueryDB> selectRecipeQueryDBIterator= selectRecipeQueryDBList.iterator();

        while( selectRecipeQueryDBIterator.hasNext())
        {
            SelectRecipeQueryDB selectRecipeQueryDBLine= selectRecipeQueryDBIterator.next();

            /*phrase = new Phrase(
                    GeneralUtils.padRight(selectRecipeQueryDBLine.getProductDescriptionName(),25)+" :  "
                            +GeneralUtils.padRight(Double.toString(selectRecipeQueryDBLine.getCalculatedQuantity()),10) +"  "+selectRecipeQueryDBLine.getUnit(), font);
            */
            phrase = new Phrase(
                    selectRecipeQueryDBLine.getUnit(), font);
            addCellToPdf ( table, phrase, TITLE_NO);
            phrase = new Phrase(
                    GeneralUtils.padRight(Double.toString(selectRecipeQueryDBLine.getCalculatedQuantity()),10), font);
            addCellToPdf ( table, phrase, TITLE_NO);
            phrase = new Phrase(
                    GeneralUtils.padRight(selectRecipeQueryDBLine.getProductDescriptionName(),25), font);
            addCellToPdf ( table, phrase, TITLE_NO);
            table.setWidths(columnWidths);
        }
        document.add(titleTable);
        document.add(table);
       // phrase = new Phrase("", font);
      //  addCellToPdf ( table,  cell,  phrase);
    }

    public static void addInstructionsToPdf ( BaseFont bf, List <SelectRecipeInstructionsDB> selectRecipeInstructionsDBList,
                                              Document document)  throws IOException, DocumentException
    {
        PdfPTable table = new PdfPTable(2);
        PdfPTable titleTable = new PdfPTable(1);
        titleTable.setHorizontalAlignment(Element.ALIGN_RIGHT) ;
        table.setHorizontalAlignment(Element.ALIGN_RIGHT) ;
        float[] columnWidths = new float[]{30f, 5f};
        titleTable.setWidthPercentage(50);
        table.setWidthPercentage(60);
        PdfPCell blankCell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        blankCell.setBorderColor( BaseColor.WHITE);
        titleTable.addCell(blankCell);
        Font font = new Font(bf, 15);
        Phrase phrase = new Phrase("הוראות", font);
        addCellToPdf ( titleTable, phrase, TITLE_YES);
        titleTable.addCell(blankCell);
        font = new Font(bf, 10);
        Iterator<SelectRecipeInstructionsDB> selectRecipeInstructionsDBIterator= selectRecipeInstructionsDBList.iterator();
        while( selectRecipeInstructionsDBIterator.hasNext())
        {
            SelectRecipeInstructionsDB selectRecipeInstructionsDBLine= selectRecipeInstructionsDBIterator.next();

            phrase = new Phrase(selectRecipeInstructionsDBLine.getInstructionDescription(), font);
            addCellToPdf ( table,  phrase, TITLE_NO);
            phrase = new Phrase(
                    (Integer.toString(selectRecipeInstructionsDBLine.getInstructionOrder())), font);
            addCellToPdf ( table,  phrase, TITLE_NO);
            table.setWidths(columnWidths);
        }
        phrase = new Phrase("", font);
        addCellToPdf ( table, phrase, TITLE_NO);
        document.add(titleTable);
        document.add(table);
    }

    public static void addProductsPlacement (  BaseFont bf,
                                             HashMap<Long, ProductsPlacement> productsPlacementHashMap,
                                             Document document) throws IOException, DocumentException
    {
        PdfPTable table = new PdfPTable(4);
        PdfPTable titleTable = new PdfPTable(1);
        titleTable.setHorizontalAlignment(Element.ALIGN_RIGHT) ;
        table.setHorizontalAlignment(Element.ALIGN_RIGHT) ;
        titleTable.setWidthPercentage(50);
        table.setWidthPercentage(50);
        float[] columnWidths = new float[]{10f, 10f, 10f, 25f};
        PdfPCell blankCell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        blankCell.setBorderColor( BaseColor.WHITE);
        titleTable.addCell(blankCell);
        Font font = new Font(bf, 15);
        Phrase phrase = new Phrase("מיקום החומרים", font);
        addCellToPdf ( titleTable, phrase, TITLE_YES);
        titleTable.addCell(blankCell);
        font = new Font(bf, 13);
        phrase = new Phrase(
                "מיקום", font);
        addCellToPdf ( table,  phrase, TITLE_NO);
        phrase = new Phrase(
                "יחידות", font);
        addCellToPdf ( table,  phrase, TITLE_NO);
        phrase = new Phrase(
                "כמות", font);
        addCellToPdf ( table, phrase, TITLE_NO);
        phrase = new Phrase(
                "חומר", font);
        addCellToPdf ( table, phrase, TITLE_NO);
        font = new Font(bf, 10);
        for (ProductsPlacement value: productsPlacementHashMap.values())
        {
            phrase = new Phrase(value.getPlacement(), font);
            addCellToPdf ( table, phrase, TITLE_NO);
            phrase = new Phrase(value.getUnit(),font);
            addCellToPdf ( table, phrase, TITLE_NO);
            phrase = new Phrase(Double.toString(value.getQuantity()),font);
            addCellToPdf ( table, phrase, TITLE_NO);
            phrase = new Phrase(value.getProductDescriptionName(), font);
            addCellToPdf ( table, phrase, TITLE_NO);
            table.setWidths(columnWidths);
        }
        phrase = new Phrase("", font);
        addCellToPdf ( table,  phrase, TITLE_NO);
        document.add(titleTable);
        document.add(table);
    }

    public static void addMissingProductsToPdf ( BaseFont bf, HashMap<Long,SelectRecipeQueryDB> productHashMap,
                                                Document document)  throws IOException, DocumentException
    {
        PdfPTable table = new PdfPTable(3);
        PdfPTable titleTable = new PdfPTable(1);
        titleTable.setHorizontalAlignment(Element.ALIGN_RIGHT) ;
        table.setHorizontalAlignment(Element.ALIGN_RIGHT) ;
        titleTable.setWidthPercentage(40);
        table.setWidthPercentage(40);
        PdfPCell blankCell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        blankCell.setBorderColor( BaseColor.WHITE);
        titleTable.addCell(blankCell);
        float[] columnWidths = new float[]{10f, 10f, 30f};
        Font font = new Font(bf, 15);
        Phrase phrase = new Phrase("חומרים חסרים למתכון", font);
        addCellToPdf ( titleTable, phrase, TITLE_YES);
        titleTable.addCell(blankCell);
        font = new Font(bf, 10);
        for (SelectRecipeQueryDB value: productHashMap.values())
        {
            phrase = new Phrase( value.getUnit(), font);
            addCellToPdf ( table, phrase, TITLE_NO);
            phrase = new Phrase(Double.toString(value.getCalculatedQuantity()), font);
            addCellToPdf ( table, phrase, TITLE_NO);
            phrase = new Phrase(value.getProductDescriptionName(), font);
            addCellToPdf ( table, phrase, TITLE_NO);
            table.setWidths(columnWidths);
        }

        phrase = new Phrase("", font);
        addCellToPdf ( table, phrase, TITLE_NO);
        document.add(titleTable);
        document.add(table);
    }
}
