package com.elalex.utils;

import com.elalex.food.model.GetSequenceDBRepository;
import com.elalex.food.model.ProductsPlacement;
import com.elalex.food.model.TransactionLogDB;
import com.elalex.food.model.TransactionLogDBRepository;

import java.util.*;

public class CreateTransactionLog
{
    public static void createTransactionLogDB(GetSequenceDBRepository getSequenceDBRepository, TransactionLogDBRepository transactionLogDBRepository,
                                              HashMap<Long, ProductsPlacement> productsPlacementHashMap,
                                              String userEmail,
                                              String recipeName,
                                              Double recipeQuantity,
                                              String recipeUnit)

    {
       long transId = getSequenceDBRepository.getSequenceId().getSequenceId();
        List<TransactionLogDB> transactionLogDB  = new ArrayList<>();
       Iterator<Map.Entry<Long, ProductsPlacement>> stockProductsIterator =  productsPlacementHashMap.entrySet().iterator();
       while  (stockProductsIterator.hasNext())
       {
           Map.Entry<Long, ProductsPlacement> stockRec = (Map.Entry<Long, ProductsPlacement>) stockProductsIterator.next();
           ProductsPlacement stockRecValues = (ProductsPlacement)stockRec.getValue();
           TransactionLogDB transLogRec = new TransactionLogDB();
           transLogRec.setTransactionId(transId);
           transLogRec.setUserEmail(userEmail);
           transLogRec.setTransactionType(transLogRec.TRANS_TYPE_GET_RECIPE_STOCK);
           transLogRec.setRecipeName(recipeName);
           transLogRec.setRecipeQuantity(recipeQuantity);
           transLogRec.setRecipeUnit(recipeUnit);
           transLogRec.setCancelInd('N');
           transLogRec.setUsedQuantity(stockRecValues.getQuantity());
           transLogRec.setStockId(stockRec.getKey());
           transactionLogDB.add(transLogRec);
       }
       transactionLogDBRepository.saveAll(transactionLogDB);
    }



}



