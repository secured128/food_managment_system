package com.elalex.utils;

import com.elalex.FileUploadController;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static com.elalex.FileUploadController.ExcelSheetsOrder.MEASURE_CONVERSION;

public class  Excel2String {


    public List<String[]> convert2String(int sizeOfParams, XSSFSheet my_worksheet, FileUploadController.ExcelSheetsOrder excelSheetsOrder) throws Exception {

        System.out.println("inside HSSFWorkbook");
        List<String[]> returnedList = new ArrayList<String[]>();
        // To iterate over the rows

        Iterator<Row> rowIterator = my_worksheet.iterator();
        if (excelSheetsOrder == MEASURE_CONVERSION) {
            handleMeasureConversion(rowIterator, sizeOfParams, returnedList);
        } else {
            //ignore header row
            rowIterator.next();
            //Loop through rows.
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row != null) {
                    handleRow(sizeOfParams, row, returnedList);
                }

            }
        }

        return returnedList;
    }

    private void handleRow(int sizeOfParams, Row row, List<String[]> returnedList) {
        //    int i=0;//String array
        //change this depending on the length of your sheet
        String[] csvdata = new String[sizeOfParams];
        int lastColumn = Math.min(row.getLastCellNum(), sizeOfParams);

        for (int cn = 0; cn < lastColumn; cn++) {
            handleCell(cn, row, csvdata, cn);
        }
        if (csvdata[0] != null) {
            returnedList.add(csvdata);
        }
    }

    private void handleCell(int cn, Row row, String[] csvdata, int csvdataIndx) {
        Cell cell = row.getCell(cn, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
        if (cell != null) {
            switch (cell.getCellType()) { //Identify CELL type
                //you need to add more code here based on
                //your requirement / transformations
                case STRING:
                    csvdata[csvdataIndx] = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    double a = (double) cell.getNumericCellValue();
                    csvdata[csvdataIndx] = String.valueOf(a);
                    break;
                case BLANK:
                    csvdata[csvdataIndx] = null;
                    break;
                case FORMULA:
                    switch (cell.getCachedFormulaResultType()) {
                        case NUMERIC:
                            double dblVal = cell.getNumericCellValue();
                            csvdata[csvdataIndx] = String.valueOf(dblVal);
                            break;
                        case STRING:
                            csvdata[csvdataIndx] = cell.getRichStringCellValue().getString();
                            break;
                    }
                    break;
            }

        }
    }

    public HashMap<String, byte[]> readImages(XSSFSheet sheet, int imageColumn) {
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

    public void handleMeasureConversion(Iterator<Row> rowIterator, int sizeOfParams, List<String[]> returnedList) {


        //Get first record which is to_unit names
        Row row = rowIterator.next();
        int lastColumn = row.getLastCellNum();
        int numberOfUnits = lastColumn - 1;
        int numOfTableRows = numberOfUnits * numberOfUnits;
        String[][] measureConversionString = new String[numOfTableRows][sizeOfParams];

        //change this depending on the length of your sheet
        String[] toUnitName = new String[1];
        for (int cn = 1; cn < lastColumn; cn++) {
            int indexOfToUnitName = cn - 1;
            handleCell(cn, row, toUnitName, 0);
            for (int countOfToUnitName = indexOfToUnitName; countOfToUnitName < numOfTableRows; countOfToUnitName = countOfToUnitName + numberOfUnits) {
                measureConversionString[countOfToUnitName][1] = toUnitName[0];
            }

        }
        //Handle rest of rows: first column is from_unit name, other fields are values.
        while (rowIterator.hasNext()) {
            row = rowIterator.next();
            int rownum = row.getRowNum();
            int fromUnitIndx = (rownum - 1) * numberOfUnits;
            System.out.println("rownum " + rownum);
            String[] cellValue = new String[1];
            if (row != null) {
                for (int cn = 0; cn < lastColumn; cn++) {
                    handleCell(cn, row, cellValue, 0);
                    //Populste from_unit value
                    if (cn == 0) {
                        for (int i = fromUnitIndx; i < fromUnitIndx + numberOfUnits; i++) {
                            measureConversionString[fromUnitIndx][0] = cellValue[0];
                            System.out.print (" "+measureConversionString[fromUnitIndx][0] );
                        }
                        System.out.println();
                    } else {
                        measureConversionString[fromUnitIndx + cn][2] = cellValue[0];
                        System.out.print (" "+measureConversionString[fromUnitIndx + cn][2]);
                    }

                   /* for (int countOfToUnitName = indexOfToUnitName; countOfToUnitName < numOfTableRows; countOfToUnitName=countOfToUnitName+numberOfUnits)
                    {
                        measureConversionString[countOfToUnitName][1]  = toUnitName[0];
                    }*/

                    //     }
                    //     }


                }
                System.out.println();
            }


        }
        for (int i=0;i<numOfTableRows;i++)
        {
            System.out.println();
            for (int j =0; j<3; j++)
            {
                System.out.print (" "+measureConversionString[i][j]);
            }
        }

    }


}

  //if (row != null)
     //     {
    //      for (int cn = 0; cn < lastColumn; cn++)
    //    {
    //    handleCell(cn, row, toUnitName, 0);
    //    if (cn==0)
    //    {
     //   for (int i=rownum; i<)
    //    }
                   /* for (int countOfToUnitName = indexOfToUnitName; countOfToUnitName < numOfTableRows; countOfToUnitName=countOfToUnitName+numberOfUnits)
                    {
                        measureConversionString[countOfToUnitName][1]  = toUnitName[0];
                    }*/

   //     }
   //     }