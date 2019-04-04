package com.elalex.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class  Excel2String
{


    public List<String[]> convert2String( int sizeOfParams, XSSFSheet my_worksheet) throws Exception
    {

        System.out.println("inside HSSFWorkbook");
        List<String[]> returnedList = new ArrayList<String[]>();
        // To iterate over the rows
        Iterator<Row> rowIterator = my_worksheet.iterator();
        //ignore header row
        rowIterator.next();
        //Loop through rows.
        while (rowIterator.hasNext())
        {
            Row row = rowIterator.next();
            if (row != null)
            {
                handleRow(sizeOfParams, row, returnedList);
            }

        }

        return returnedList;
    }

    private void handleRow(int sizeOfParams, Row row, List<String[]> returnedList)
    {
        //    int i=0;//String array
        //change this depending on the length of your sheet
        String[] csvdata = new String[sizeOfParams];
        int lastColumn = Math.min(row.getLastCellNum(), sizeOfParams);

        for (int cn = 0; cn < lastColumn; cn++)
        {
            handleCell(cn, row, csvdata);
        }
        if (csvdata[0] != null)
        {
            returnedList.add(csvdata);
        }
    }

    private void handleCell(int cn, Row row, String[] csvdata )
    {
        Cell cell = row.getCell(cn, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
        if (cell != null)
        {
            switch (cell.getCellType())
            { //Identify CELL type
                //you need to add more code here based on
                //your requirement / transformations
                case STRING:
                    csvdata[cn] = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    double a = (double) cell.getNumericCellValue();
                    csvdata[cn] = String.valueOf(a);
                    break;
                case BLANK:
                    csvdata[cn] = null;
                    break;
            }

        }
    }

    public  HashMap<String, byte[]> readImages(XSSFSheet sheet, int imageColumn) {
        int idColumn = 0;
        HashMap<String, byte[]> picturesMap = new HashMap<>();
        XSSFDrawing drawing = sheet.getDrawingPatriarch();
        if (drawing != null) {
            for (XSSFShape shape : drawing.getShapes()) {
                if (shape instanceof XSSFPicture) {
                    XSSFPicture picture = (XSSFPicture) shape;
                    XSSFClientAnchor anchor = (XSSFClientAnchor) picture.getAnchor();

                    // Ensure to use only relevant pictures
                    short pictPlace = anchor.getCol1();
                    if (pictPlace == imageColumn) {

                        // Use the row from the anchor
                        XSSFRow pictureRow = sheet.getRow(anchor.getRow1());
                        if (pictureRow != null) {
                            XSSFCell idCell = pictureRow.getCell(idColumn);
                            if (idCell != null) {
                                String idString = idCell.getStringCellValue();
                                XSSFPictureData data = picture.getPictureData();
                                byte picData[] = data.getData();
                                picturesMap.put(idString, picData);
                            }
                        }
                    }
                }
            }
        }
            return picturesMap;
        }

}