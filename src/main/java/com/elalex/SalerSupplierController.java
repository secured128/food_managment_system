package com.elalex;

import com.elalex.utils.CreatePdfFile;
import com.elalex.utils.Excel2String;
import com.elalex.utils.GeneralUtils;
import com.elalex.food.model.SupplierDB;
import com.elalex.food.model.SupplierDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * https://spring.io/guides/tutorials/bookmarks/
 */
@RestController
public class SalerSupplierController {

    @Autowired
    private SupplierDBRepository supplierDBRepository;

    @RequestMapping(value = "/supplier/{id}", method = GET)
    @ResponseBody
    public SupplierDB getSupplierById(@PathVariable long id, HttpServletResponse response) {
        GeneralUtils.addHeader(response);
        Optional<SupplierDB> supplierDB = supplierDBRepository.findById(id);
        return supplierDB.get();
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

    @RequestMapping(method = GET, path = "/uploadFile")

        public ResponseEntity<List<SupplierDB>> readFile( HttpServletResponse response, String url ) {
             try {
                 GeneralUtils.addHeader(response);
                 System.out.println("url "+url);
                 int sizeOfParams=8;
                 List<SupplierDB> suppliersList = new ArrayList<>();
                 System.out.println(url);
                 Excel2String excel2Csv = new Excel2String();
                 List<String[]> suppDb = excel2Csv.convert2String( url, sizeOfParams);
                 Iterator <String[]> iterator = suppDb.iterator();

                 while (iterator.hasNext()) {
                    String[] nextRow = iterator.next();
                    SupplierDB nextSupplDb = new SupplierDB(nextRow);
                     nextSupplDb = supplierDBRepository.save(nextSupplDb);
                     suppliersList.add(nextSupplDb);
                 }
                 CreatePdfFile.createPdfFile();
                 return ResponseEntity.ok(suppliersList);
             }
     catch (Exception e) {
        return ResponseEntity.badRequest().build();
        }


}}