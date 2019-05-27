package com.elalex;

import com.elalex.food.model.TransactionLogSelectCreatedRecipesDB;
import com.elalex.food.model.TransactionLogSelectCreatedRecipesDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * https://spring.io/guides/tutorials/bookmarks/
 */
@RestController
public class SelectTransactionLogController {

    @Autowired
    private TransactionLogSelectCreatedRecipesDBRepository selectTransactionLogSelectCreatedRecipesDBRepository;

    @RequestMapping(method = GET, path = "/selectTransactionLog")
    //ResponseEntity<List<SelectRecipeQueryDB>>

    public ResponseEntity<List<TransactionLogSelectCreatedRecipesDB>> selectTransactionLog(HttpServletResponse response,
                                                           String userEmail,
                                                           String fromDate,
                                                           String toDate) {
        try {

            System.out.print("fromDate "+fromDate);
            System.out.print("toDate "+toDate);
            List<TransactionLogSelectCreatedRecipesDB> transactionLogDB =
                    selectTransactionLogSelectCreatedRecipesDBRepository.selectTransactionLog(userEmail, fromDate, toDate);
            System.out.print("after selectTransactionLog ");
           // return ResponseEntity.ok(selectRecipeQueryDBList);
            if (transactionLogDB != null) {
                List<TransactionLogSelectCreatedRecipesDB> transactionLogReturnList = new ArrayList<>();
                Iterator<TransactionLogSelectCreatedRecipesDB> transactionLogIterator = transactionLogDB.iterator();
                while (transactionLogIterator.hasNext()) {
                    transactionLogReturnList.add(transactionLogIterator.next());
                }
                return ResponseEntity.ok(transactionLogReturnList);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


}