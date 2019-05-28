package com.elalex;

import com.elalex.food.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * https://spring.io/guides/tutorials/bookmarks/
 */
@RestController
public class SelectTransactionLogController {

    @Autowired
    private TransactionLogSelectCreatedRecipesDBRepository selectTransactionLogSelectCreatedRecipesDBRepository;

    @RequestMapping(method = GET, path = "/selectTransactionLog")

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

    @Autowired
    private TransactionLogDBRepository selectTransactionLogDBRepository;
    @Autowired
    private StockDBRepository stockDBRepository;

    @RequestMapping(method = GET, path = "/cancelTransaction")

    public synchronized void cancelTransaction(HttpServletResponse response,
                                               Long transactionId) {
        try {

           List<TransactionLogDB> transactionLogDBList =
                    selectTransactionLogDBRepository.selectTransactionLogByTransId(transactionId);
            System.out.print("after selectTransactionLog ");
            // return ResponseEntity.ok(selectRecipeQueryDBList);
            if (transactionLogDBList != null) {
                System.out.print("in update  ");
                Iterator<TransactionLogDB> transactionLogIterator = transactionLogDBList.iterator();
                List <Long> stockIds = new ArrayList<Long>();
                HashMap<Long,BigDecimal> stockTransLogAmtMap = new HashMap<>();
                TransactionLogDB transLogRec = null;
                while (transactionLogIterator.hasNext()) {
                    transLogRec = transactionLogIterator.next();
                    stockIds.add(transLogRec.getStockId());
                    stockTransLogAmtMap.put(transLogRec.getStockId(),BigDecimal.valueOf(transLogRec.getUsedQuantity()) );
                    transLogRec.setCancelInd("Y");
                    transLogRec.setCancellationTime(LocalDateTime.now());
                }
                Iterable<StockDB> stockDBList = stockDBRepository.findAllById(stockIds);
                Iterator<StockDB> stockDBListItrator = stockDBList.iterator();
                StockDB stockDBRec = null;
                BigDecimal stockUsedAmount = new BigDecimal(0);
                while (stockDBListItrator.hasNext())
                {
                    stockDBRec= stockDBListItrator.next();
                    stockUsedAmount = BigDecimal.valueOf(stockDBRec.getUsedQuantity()).subtract(stockTransLogAmtMap.get(stockDBRec.getId()));
                    stockDBRec.setUsedQuantity(stockUsedAmount.doubleValue());
                }
                stockDBRepository.saveAll(stockDBList);
                selectTransactionLogDBRepository.saveAll(transactionLogDBList);
            } else {

        }
          }
        catch (Exception e) {
            System.out.print("error");
        }
    }

}