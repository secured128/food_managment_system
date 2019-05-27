
package com.elalex;

import com.elalex.food.model.*;
import com.elalex.utils.CheckStock;
import com.elalex.utils.CreatePdfFile;
import com.elalex.utils.CreateTransactionLog;
import com.elalex.utils.TestMemory;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
    @Autowired
    private RecipeDescriptionDBRepository recipeDescriptionDBRepository;
    @Autowired
    private TransactionLogDBRepository transactionLogDBRepository;
    @Autowired
    private GetSequenceDBRepository getSequenceDBRepository;


    @RequestMapping(method = GET, path = "/selectRecipeQueryUpdateStock")
    //ResponseEntity<List<SelectRecipeQueryDB>>

    public synchronized  void selectRecipeQueryUpdateStock(HttpServletResponse response, Long recipeId,
                                                                                               Double quantity,
                                                                                               String userEmail,
                                                                                               String updateStockInd) {
        try {

            RecipeDescriptionDB recipeDescriptionDB = recipeDescriptionDBRepository.findById(recipeId).get();
            String recipeName = recipeDescriptionDB.getRecipeName();
            List<SelectRecipeQueryDB> selectRecipeQueryDBList = selectRecipeQueryDBRepository.selectRecipeProductLinked(recipeName, quantity);

            List<SelectRecipeInstructionsDB> selectRecipeInstructionsDBList = selectRecipeInstructionsDBRepository.selectRecipeInstructions(recipeName);


            HashMap<Long,SelectRecipeQueryDB> productHashMap = new HashMap<>();
            HashMap<Long,ProductsPlacement> productsPlacementHashMap = new HashMap<>();
            CheckStock checkStock = new CheckStock();
            List <StockDB> stockList =  checkStock.checkStockProducts(selectRecipeQueryDBList, productHashMap, productsPlacementHashMap, stockDBRepository);
            String fileName = CreatePdfFile.createPdfFile(selectRecipeQueryDBList, selectRecipeInstructionsDBList, recipeName, productHashMap, productsPlacementHashMap, recipeDescriptionDB);
            sendEmailController.sendEmail(fileName);
            GetSequenceDB transId = null;
            if ( updateStockInd.equals("YES") && (stockList != null) && (productHashMap.isEmpty()))//means that there is enough products in stock for recipe
            {
                stockDBRepository.saveAll(stockList);
                CreateTransactionLog.createTransactionLogDB( getSequenceDBRepository,  transactionLogDBRepository,
                         productsPlacementHashMap,
                         userEmail,
                         recipeName);
            }
            TestMemory.testMem();
            System.out.printf("before returnPdfFile");
            returnPdfFile(fileName, response);
            System.out.printf("after returnPdfFile");
           // return ResponseEntity.ok(selectRecipeQueryDBList);
        } catch (Exception e) {
           // return ResponseEntity.badRequest().build();
        }
    }

    private void returnPdfFile( String fileName, HttpServletResponse response)
    {
        System.out.printf("inside returnPdfFile");
        File file = new File(fileName);
        try {
            InputStream myStream = new URL(file.toURI().toString()).openStream();
// Set the content type and attachment header.
            response.addHeader("Content-disposition", "attachment;filename=" + fileName);
            response.setContentType("application/pdf");

// Copy the stream to the response's output stream.
            IOUtils.copy(myStream, response.getOutputStream());
            response.flushBuffer();
        }
        catch (Exception e) {

        }
    }


}