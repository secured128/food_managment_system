package com.elalex;

import com.elalex.utils.GeneralUtils;
import com.elalex.food.model.SupplierDB;
import com.elalex.food.model.SupplierDBRepository;
import com.elalex.food.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.net.*;
import java.io.*;

import static java.lang.System.in;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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

    @RequestMapping(method = POST, path = "/uploadFile/{url}")

        public ResponseEntity<List<SupplierDB>> readFile(@PathVariable String url ,HttpServletResponse response) {
             try {
                 System.out.println("hiii");
                 List<SupplierDB> suppliersList = new ArrayList<>();
                 URL fileName = new URL(url);
                 System.out.println(url);
                 BufferedReader in = new BufferedReader(
                         new InputStreamReader(fileName.openStream()));

                 String inputLine;
                 while ((inputLine = in.readLine()) != null)
                     System.out.println(inputLine);
                 in.close();
                 return ResponseEntity.ok(suppliersList);
             }
     catch (Exception e) {
        return ResponseEntity.badRequest().build();
        }


}}