package com.elalex.utils;

import com.elalex.food.model.ProductsPlacement;
import com.elalex.food.model.SelectRecipeQueryDB;
import com.elalex.food.model.StockDB;
import com.elalex.food.model.StockDBRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CheckStock {


    public CheckStock(StockDBRepository stockDBRepository) {

    }

    public CheckStock() {

    }

    public List <StockDB> checkStockProducts(List<SelectRecipeQueryDB> selectRecipeQueryDBList, HashMap<Long,SelectRecipeQueryDB> productHashMap, HashMap<Long, ProductsPlacement> productsPlacementHashMap, StockDBRepository stockDBRepository)
    {
        List<Long> productIds = new ArrayList<>();

        Iterator productsIterator = selectRecipeQueryDBList.iterator();
        while (productsIterator.hasNext())
        {
            SelectRecipeQueryDB nextRecipeProductRec = new  SelectRecipeQueryDB((SelectRecipeQueryDB)productsIterator.next());
            productIds.add(nextRecipeProductRec.getProductId());
            productHashMap.put(nextRecipeProductRec.getProductId(),(SelectRecipeQueryDB)nextRecipeProductRec);
        }

        List <StockDB>stockDBRepositoryList =  stockDBRepository.selectStockQuery(productIds);
        Iterator stockDBIterator = stockDBRepositoryList.iterator();
        BigDecimal stockLeftAmount = new BigDecimal(0);
        BigDecimal stockUsedAmount = new BigDecimal(0);
        BigDecimal recipeProductAmount = new BigDecimal(0);
        while  (stockDBIterator.hasNext())
        {
            StockDB stockDBIteratorRec = (StockDB)stockDBIterator.next();
            stockLeftAmount = BigDecimal.valueOf(stockDBIteratorRec.getQuantity()).subtract(BigDecimal.valueOf(stockDBIteratorRec.getUsedQuantity()));
            stockUsedAmount = BigDecimal.valueOf(stockDBIteratorRec.getUsedQuantity());
            recipeProductAmount= BigDecimal.valueOf(productHashMap.get(stockDBIteratorRec.getProductId()).getCalculatedQuantity());
            SelectRecipeQueryDB recipeProductRec = productHashMap.get(stockDBIteratorRec.getProductId());
            if (stockLeftAmount.compareTo(recipeProductAmount)==1)
            {
                stockDBIteratorRec.setUsedQuantity(stockUsedAmount.add(recipeProductAmount).doubleValue());
                recipeProductRec.setCalculatedQuantity(0d);
                updateProductPlacementRecord(productsPlacementHashMap,recipeProductAmount, stockDBIteratorRec, recipeProductRec);
            }
            else
            {
                recipeProductRec.setCalculatedQuantity(recipeProductAmount.subtract(stockLeftAmount).doubleValue());
                stockDBIteratorRec.setUsedQuantity(stockDBIteratorRec.getQuantity());
                updateProductPlacementRecord(productsPlacementHashMap, stockLeftAmount,  stockDBIteratorRec, recipeProductRec);
            }
            productHashMap.put(stockDBIteratorRec.getProductId(),recipeProductRec);
        }
       return stockDBRepositoryList;

    }

    private void updateProductPlacementRecord(HashMap<Long,ProductsPlacement> productsPlacementHashMap, BigDecimal recipeProductAmount, StockDB stockDBIteratorRec, SelectRecipeQueryDB recipeProductRec)
    {
        ProductsPlacement productsPlacement;
        if(productsPlacementHashMap.containsKey(stockDBIteratorRec.getId()))
        {
            productsPlacement = productsPlacementHashMap.get(stockDBIteratorRec.getId());
            productsPlacement.setQuantity(recipeProductAmount.add(BigDecimal.valueOf(productsPlacement.getQuantity())).doubleValue());
            productsPlacementHashMap.put(stockDBIteratorRec.getId(),productsPlacement);
        }
        else
        {
            populateProductsPlacement(productsPlacementHashMap, recipeProductAmount, stockDBIteratorRec, recipeProductRec );
        }
    }

    private void populateProductsPlacement(HashMap<Long,ProductsPlacement> productsPlacementHashMap, BigDecimal recipeProductAmount, StockDB stockDBIteratorRec, SelectRecipeQueryDB recipeProductRec)
    {
        ProductsPlacement productsPlacement = new ProductsPlacement();
        productsPlacement.setQuantity(recipeProductAmount.doubleValue());
        productsPlacement.setPlacement(stockDBIteratorRec.getPlacement());
        productsPlacement.setProductDescriptionName(recipeProductRec.getProductDescriptionName());
        productsPlacement.setUnit(recipeProductRec.getUnit());
        productsPlacementHashMap.put(stockDBIteratorRec.getId(),productsPlacement);

    }

}
