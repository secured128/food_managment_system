
package com.elalex;

import com.elalex.food.model.*;
import com.elalex.utils.CheckStock;
import com.elalex.utils.CreatePdfFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * https://spring.io/guides/tutorials/bookmarks/
 */
@RestController
public class SelectRecipeController {

    @Autowired
    private SelectRecipeQueryDBRepository selectRecipeQueryDBRepository;
    @Autowired
    private SelectRecipeInstructionsDBRepository selectRecipeInstructionsDBRepository;
    @Autowired
    private StockDBRepository stockDBRepository;

    @RequestMapping(method = GET, path = "/selectRecipeQueryUpdateStock")

    public  ResponseEntity<List<SelectRecipeQueryDB>> selectRecipeQueryUpdateStock(HttpServletResponse response, String recipeName, Double quantity ) {
        try {

            List<SelectRecipeQueryDB> selectRecipeQueryDBList = selectRecipeQueryDBRepository.selectRecipeProductLinked(recipeName, quantity);

            List<SelectRecipeInstructionsDB> selectRecipeInstructionsDBList = selectRecipeInstructionsDBRepository.selectRecipeInstructions(recipeName);

            HashMap<Long,SelectRecipeQueryDB> productHashMap = new HashMap<>();
            HashMap<Long,ProductsPlacement> productsPlacementHashMap = new HashMap<>();
            CheckStock checkStock = new CheckStock();
            checkStock.checkStockProducts(selectRecipeQueryDBList, productHashMap, productsPlacementHashMap, stockDBRepository);
            String fileName = CreatePdfFile.createPdfFile(selectRecipeQueryDBList, selectRecipeInstructionsDBList, recipeName, productHashMap, productsPlacementHashMap);
            sendEmailController.sendEmail(fileName);
            return ResponseEntity.ok(selectRecipeQueryDBList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }



}