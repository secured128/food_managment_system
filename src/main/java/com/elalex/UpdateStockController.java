
package com.elalex;

import com.elalex.food.model.*;
import com.elalex.utils.CheckStock;
import com.elalex.utils.CreatePdfFile;
import com.elalex.utils.CreateTransactionLog;
import com.elalex.utils.GeneralUtils;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * https://spring.io/guides/tutorials/bookmarks/
 */
@RestController
public class UpdateStockController {

    @Autowired
    private SupplierDBRepository supplierDBRepository;
    @Autowired
    private ProductDBRepository productDBRepository;




    @RequestMapping(method = GET, path = "/getSupplierProduct")
    public ResponseEntity<HashMap<String,List<ProductDB>> > getSupplierProduct(HttpServletResponse response) {
        GeneralUtils.addHeader(response);
        try {

            HashMap<String,List<ProductDB>>  supplierProductHashMap = new HashMap<>();

            Iterable<SupplierDB> suppliersAll = supplierDBRepository.findAll();
            Iterable<ProductDB> productsAll = productDBRepository.findAll();
            if (suppliersAll != null && productsAll != null)
            {
                Iterator <SupplierDB> supplierIterator = suppliersAll.iterator();
                while (supplierIterator.hasNext())
                {
                    SupplierDB supplierRec = supplierIterator.next();
                    Iterator <ProductDB> productsIterator = productsAll.iterator();
                    List<ProductDB> productListForSupplier = new ArrayList();
                    while (productsIterator.hasNext())
                    {
                        ProductDB productRec = productsIterator.next();
                        if (productRec.getIdSupplier().equals(supplierRec.getId()))
                        {
                            productListForSupplier.add(productRec);
                        }
                    }
                    supplierProductHashMap.put(supplierRec.getName(),productListForSupplier);
                }
                return ResponseEntity.ok(supplierProductHashMap);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }



}