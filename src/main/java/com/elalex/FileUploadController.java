package com.elalex;

import com.elalex.food.model.*;
import com.elalex.utils.Excel2String;
import com.elalex.utils.GeneralUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/*
This file is responsible to upload excel with all reference tables:
units
supplier
product
recipe_description
instructions_description
recipe_instructions_order
recipe_products
1) delete all data fro tables above
2) upload data from excel
 */
@RestController
public class FileUploadController {
    @Autowired
    private UnitsDBRepository unitsDBRepository;
    @Autowired
    private SupplierDBRepository supplierDBRepository;
    @Autowired
    private ProductDBRepository productDBRepository;
    @Autowired
    private RecipeDescriptionDBRepository recipeDescriptionDBRepository;
    @Autowired
    private InstructionsDescriptionDBRepository instructionsDescriptionDBRepository;
    @Autowired
    private RecipeInstructionsOrderDBRepository recipeInstructionsOrderDBRepository;
    @Autowired
    private RecipeProductsDBRepository recipeProductsDBRepository;

@RequestMapping(method = GET, path = "/uploadGeneralFile")

    public ResponseEntity uploadExcelFileToDB(HttpServletResponse response, String url ) {
        try {
            GeneralUtils.addHeader(response);
            System.out.println("url "+url);

            deleteAllTables();

            //First we read the Excel file in binary format into FileInputStream
            //FileInputStream input_document = new FileInputStream(new File("excel_to_csv.xls"));
            InputStream input_document = new URL(url).openStream();

            // Read workbook into HSSFWorkbook
            //ZipSecureFile.setMinInflateRatio(-1.0d);
            XSSFWorkbook my_xls_workbook = new XSSFWorkbook(input_document);
            for(ExcelSheetsOrder excelSheetsOrder: ExcelSheetsOrder.values())
            {
                int sheetNumber = excelSheetsOrder.ordinal();
                int numOfParams = setNumOfParams(excelSheetsOrder);

                Excel2String excel2Csv = new Excel2String();
                XSSFSheet my_worksheet = my_xls_workbook.getSheetAt(sheetNumber);

                List<String[]> sheetValues = excel2Csv.convert2String(numOfParams, my_worksheet );
                int imageColumn = setImageColumn(excelSheetsOrder);
                HashMap<String, byte[]> picturesMap = null;
                if (imageColumn>0) {
                    picturesMap = excel2Csv.readImages(my_worksheet, imageColumn);
                }
                uploadToDB(sheetValues, picturesMap, excelSheetsOrder );

            }
            input_document.close(); //close xls
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    private void deleteAllTables()
    {
        System.out.println ("deleting tables");
        recipeProductsDBRepository.deleteAll();
        recipeInstructionsOrderDBRepository.deleteAll();
        instructionsDescriptionDBRepository.deleteAll();
        recipeDescriptionDBRepository.deleteAll();
        productDBRepository.deleteAll();
        supplierDBRepository.deleteAll();
        unitsDBRepository.deleteAll();
    }

    private int  setNumOfParams(ExcelSheetsOrder excelSheetsOrder)
    {
        int numOfParams = 0;
        switch (excelSheetsOrder)
        {
            case UNITS:
                numOfParams = UnitsDB.NUMBER_OF_PARAMS;
                break;
            case SUPPLIERS:
                numOfParams = SupplierDB.NUMBER_OF_PARAMS;
                break;
            case PRODUCT:
                numOfParams = ProductDB.NUMBER_OF_PARAMS;
                break;
            case RECIPE_DESCRIPTION:
                numOfParams = RecipeDescriptionDB.NUMBER_OF_PARAMS;
                break;
            case INSTRUCTIONS_DESCRIPTION:
                numOfParams = InstructionsDescriptionDB.NUMBER_OF_PARAMS;
                break;
            case RECIPE_INSTRUCTIONS_ORDER:
                numOfParams = RecipeInstructionsOrderDB.NUMBER_OF_PARAMS;
                break;
            case RECIPE_PRODUCTS:
                numOfParams = RecipeProductsDB.NUMBER_OF_PARAMS;
                break;
            default:
                break;
        }
        return numOfParams;
    }

    private int  setImageColumn(ExcelSheetsOrder excelSheetsOrder)
    {
        int imageColumn = 0;
        switch (excelSheetsOrder)
        {
            case RECIPE_DESCRIPTION:
                imageColumn = RecipeDescriptionDB.IMAGE_COLUMN;
                break;
            default:
                break;
        }
        return imageColumn;
    }

    private void  uploadToDB( List<String[]> sheetValues, HashMap<String, byte[]> picturesMap, ExcelSheetsOrder excelSheetsOrder)
    {
        Iterator<String[]> iterator = sheetValues.iterator();
        switch (excelSheetsOrder)
        {
            case UNITS:
                while (iterator.hasNext())
                {
                    String[] nextRow = iterator.next();
                    UnitsDB nextRowDB = new UnitsDB(nextRow);
                    unitsDBRepository.save(nextRowDB);
                }
                break;
            case SUPPLIERS:
                while (iterator.hasNext())
                {
                    String[] nextRow = iterator.next();
                    SupplierDB nextRowDB = new SupplierDB(nextRow);
                    supplierDBRepository.save(nextRowDB);
                }
                break;
            case PRODUCT:
                while (iterator.hasNext())
                {
                    String[] nextRow = iterator.next();
                    ProductDB nextRowDB = new ProductDB(nextRow);
                    productDBRepository.save(nextRowDB);
                }
                break;
            case RECIPE_DESCRIPTION:
                while (iterator.hasNext())
                {
                    String[] nextRow = iterator.next();
                    RecipeDescriptionDB nextRowDB = new RecipeDescriptionDB(nextRow, picturesMap);
                    recipeDescriptionDBRepository.save(nextRowDB);
                }
                break;
            case INSTRUCTIONS_DESCRIPTION:
                while (iterator.hasNext())
                {
                    String[] nextRow = iterator.next();
                    InstructionsDescriptionDB nextRowDB = new InstructionsDescriptionDB(nextRow);
                    instructionsDescriptionDBRepository.save(nextRowDB);
                }
                break;
            case RECIPE_INSTRUCTIONS_ORDER:
                while (iterator.hasNext())
                {
                    String[] nextRow = iterator.next();
                    RecipeInstructionsOrderDB nextRowDB = new RecipeInstructionsOrderDB(nextRow);
                    recipeInstructionsOrderDBRepository.save(nextRowDB);
                }
                break;
            case RECIPE_PRODUCTS:
                while (iterator.hasNext())
                {
                    String[] nextRow = iterator.next();
                    RecipeProductsDB nextRowDB = new RecipeProductsDB(nextRow);
                    recipeProductsDBRepository.save(nextRowDB);
                }
                break;
            default:
                break;
        }
    }

    public enum ExcelSheetsOrder{
        UNITS, SUPPLIERS, PRODUCT, RECIPE_DESCRIPTION,
        INSTRUCTIONS_DESCRIPTION, RECIPE_INSTRUCTIONS_ORDER, RECIPE_PRODUCTS    }
}