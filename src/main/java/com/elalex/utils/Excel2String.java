package com.elalex.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class  Excel2String
{


    public List<String[]> convert2String(XSSFWorkbook my_xls_workbook, int sizeOfParams, int sheetNumber) throws Exception
    {


        System.out.println("inside HSSFWorkbook");
        // Read worksheet into HSSFSheet
        XSSFSheet my_worksheet = my_xls_workbook.getSheetAt(sheetNumber);
        List<String[]> returnedList = new ArrayList<String[]>();
        // To iterate over the rows
        Iterator<Row> rowIterator = my_worksheet.iterator();
        rowIterator.next();
        // OpenCSV writer object to create CSV file
        //   FileWriter my_csv=new FileWriter("convertedCSVFile.csv");

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
}