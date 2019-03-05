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

public class  Excel2String {


    public  List<String[]> convert2String(String url, int sizeOfParams) throws Exception{

        //First we read the Excel file in binary format into FileInputStream
        //FileInputStream input_document = new FileInputStream(new File("excel_to_csv.xls"));
        InputStream input_document = new URL(url).openStream();

        // Read workbook into HSSFWorkbook
        //ZipSecureFile.setMinInflateRatio(-1.0d);
        XSSFWorkbook my_xls_workbook = new XSSFWorkbook(input_document);
        System.out.println("inside HSSFWorkbook");
        // Read worksheet into HSSFSheet
        XSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0);
        List<String[]> returnedList = new ArrayList<String[]>();
        // To iterate over the rows
        Iterator<Row> rowIterator = my_worksheet.iterator();
        rowIterator.next();
        // OpenCSV writer object to create CSV file
        //   FileWriter my_csv=new FileWriter("convertedCSVFile.csv");

        //Loop through rows.
        while(rowIterator.hasNext()) {
            Row row = rowIterator.next();
            int i=0;//String array
            //change this depending on the length of your sheet
            String[] csvdata = new String[sizeOfParams];
            Iterator<Cell> cellIterator = row.cellIterator();
            while(cellIterator.hasNext()) {
                Cell cell = cellIterator.next(); //Fetch CELL
                switch(cell.getCellType()) { //Identify CELL type
                    //you need to add more code here based on
                    //your requirement / transformations
                    case STRING:
                        csvdata[i]= cell.getStringCellValue();
                        break;
                    case NUMERIC:
                        long a =(long)cell.getNumericCellValue();
                        csvdata[i] = String.valueOf(a);
                        break;
                    case BLANK:
                        csvdata[i]= "";
                        break;
                }
                i=i+1;
            }
           returnedList.add(csvdata);
        }
        input_document.close(); //close xls
        return returnedList;
    }
}
