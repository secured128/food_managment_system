package com.elalex;

import com.elalex.food.model.RecipeDescriptionDB;
import com.elalex.food.model.RecipeDescriptionDBRepository;
import com.elalex.utils.CreatePdfFile;
import com.elalex.utils.Excel2String;
import com.elalex.utils.GeneralUtils;
import com.elalex.food.model.SupplierDB;
import com.elalex.food.model.SupplierDBRepository;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * https://spring.io/guides/tutorials/bookmarks/
 */
@RestController
public class SuppliersController {

    @Autowired
    private SupplierDBRepository supplierDBRepository;
    @Autowired
    private RecipeDescriptionDBRepository recipeDescriptionDBRepository;

    @RequestMapping(value = "/supplier/{id}", method = GET)
    @ResponseBody
    public SupplierDB getSupplierById(@PathVariable long id, HttpServletResponse response) {
        GeneralUtils.addHeader(response);
        Optional<SupplierDB> supplierDB = supplierDBRepository.findById(id);
        return supplierDB.get();
    }
    @RequestMapping(value = "/supplier/getByName", method = GET)
    @ResponseBody
    public ResponseEntity<List<SupplierDB>>  getSupplierByName( HttpServletResponse response, String name) {
        GeneralUtils.addHeader(response);
        List<SupplierDB> supplierDBList = supplierDBRepository.findByNameStartsWithIgnoreCase(name);
        return ResponseEntity.ok(supplierDBList);
    }

    @RequestMapping(method = GET, path = "/supplier/findAll")
    public ResponseEntity<List<SupplierDB>> findAll(HttpServletResponse response) {
        GeneralUtils.addHeader(response);
        try {
            List<SupplierDB> suppliersList = new ArrayList<>();
            Iterable<SupplierDB> suppliersAll = supplierDBRepository.findAll();
            if (suppliersAll != null) {
                Iterator<SupplierDB> supplierIterator = suppliersAll.iterator();
                while (supplierIterator.hasNext()) {
                    suppliersList.add(supplierIterator.next());
                }
                return ResponseEntity.ok(suppliersList);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(method = GET, path = "/getAllRecipes")
    public ResponseEntity<List<RecipeDescriptionDB>> getAllRecipes(HttpServletResponse response) {
        GeneralUtils.addHeader(response);
        try {
            List<RecipeDescriptionDB> suppliersList = new ArrayList<>();

            Iterable<RecipeDescriptionDB> recipeAll = recipeDescriptionDBRepository.findAll();
            if (recipeAll != null) {
                Iterator<RecipeDescriptionDB> supplierIterator = recipeAll.iterator();
                while (supplierIterator.hasNext()) {
                    suppliersList.add(supplierIterator.next());
                }
                return ResponseEntity.ok(suppliersList);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }




  /*  @RequestMapping(method = GET, path = "/uploadFile/Supplier")

    public ResponseEntity<List<SupplierDB>> readFile( HttpServletResponse response, String url ) {
        try {
            GeneralUtils.addHeader(response);
            System.out.println("url "+url);
            int sizeOfParams=8;
            List<SupplierDB> suppliersList = new ArrayList<>();
            System.out.println(url);
            InputStream input_document = new URL(url).openStream();
            XSSFWorkbook my_xls_workbook = new XSSFWorkbook(input_document);
            Excel2String excel2Csv = new Excel2String();
            XSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0);
            List<String[]> suppDb = excel2Csv.convert2String( sizeOfParams, my_worksheet, 1);
            Iterator <String[]> iterator = suppDb.iterator();

            while (iterator.hasNext()) {
                String[] nextRow = iterator.next();
                SupplierDB nextSupplDb = new SupplierDB(nextRow);
                nextSupplDb = supplierDBRepository.save(nextSupplDb);
                suppliersList.add(nextSupplDb);
            }
            input_document.close();
           // CreatePdfFile.createPdfFile();
            return ResponseEntity.ok(suppliersList);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }


    }*/
}