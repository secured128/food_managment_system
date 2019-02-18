package com.elalex;

import com.elalex.food.model.SupplierDB;
import com.elalex.food.model.SupplierDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * https://spring.io/guides/tutorials/bookmarks/
 */
@RestController
public class SalerSupplierController {

    @Autowired
    private SupplierDBRepository supplierDBRepository;


    @RequestMapping(method = POST, path = "/loginS")
    public ResponseEntity<SupplierDB> login(@RequestBody SupplierDB supplierToFind) {
        try {
            SupplierDB supplier = supplierDBRepository.findByName(supplierToFind.getName());
            if (supplier != null) {
                return ResponseEntity.ok(supplier);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(method = GET, path = "/findS/{nameStart}")
    public ResponseEntity<List<SupplierDB>> find(@PathVariable String nameStart) {
        try {
            List<SupplierDB> suppliers = supplierDBRepository.findByNameStartsWithIgnoreCase(nameStart);
            if (suppliers != null) {
                return ResponseEntity.ok(suppliers);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(method = GET, path = "/Supplier/find")
    public ResponseEntity<List<SupplierDB>> find() {
        try {
            List<SupplierDB> supplierList = new ArrayList<>();
            Iterable<SupplierDB> suppliers = supplierDBRepository.findAll();
            if (suppliers != null) {
                Iterator<SupplierDB> SupplierIterator = suppliers.iterator();
                while (SupplierIterator.hasNext()) {
                    supplierList.add(SupplierIterator.next());
                }
                return ResponseEntity.ok(supplierList);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /*
    see : http://www.baeldung.com/spring-requestmapping

    1. insert Supplier
    INSERT INTO Suppliers(	id, name)	VALUES (1,'ALEX');

    2. call API
    http://localhost:8090/Supplier/1
     */
    @RequestMapping(value = "/Supplier/{id}", method = GET)
    @ResponseBody
    public ResponseEntity<SupplierDB> getSupplier(@PathVariable long id) {
        SupplierDB supplier = supplierDBRepository.findOne(id);
        if (supplier != null)
        {
            return ResponseEntity.ok(supplier);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/Supplier/create/{name}", method = GET)
    @ResponseBody
    public String CreateSupplier(@PathVariable String name, String address, String city, String telephone, String email, String companyNo) {
        SupplierDB supplier = new SupplierDB(name, address, city, telephone, email, companyNo);
        supplier = supplierDBRepository.save(supplier);
        return supplier.toString();
    }

    @RequestMapping(method = POST, path = "/Supplier/save")
    public ResponseEntity<SupplierDB> save(@RequestBody SupplierDB supplierToCreate) {
        try {
            SupplierDB supplier = null;
            if(supplierToCreate.getId()>0){
                supplier = supplierDBRepository.findOne(supplierToCreate.getId());
                supplier.setName(supplierToCreate.getName());
            }else {
                String json1 = "23";
                supplier = new SupplierDB( json1);
            }
            supplier = supplierDBRepository.save(supplier);
            if (supplier != null) {
                return ResponseEntity.ok(supplier);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @RequestMapping(method = POST, path = "/Supplier/delete")
    public ResponseEntity<HttpStatus> delete(@RequestBody SupplierDB SupplierToCreate) {
        try {
            SupplierDB supplier = supplierDBRepository.findOne(SupplierToCreate.getId());
            supplierDBRepository.delete(supplier);
            if (supplier != null) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
//    @RequestMapping(value = "/Supplier", method = RequestMethod.POST)
//    ResponseEntity<Supplier> add(@RequestBody Supplier SupplierToFind) {
//        try {
//            Supplier Supplier = SuppliersRepository.findByNameAndPassword(SupplierToFind.getName(), SupplierToFind.getPassword());
//            if (Supplier != null) {
//                return ResponseEntity.ok(Supplier);
//            } else {
//                return ResponseEntity.notFound().build();
//            }
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }


}