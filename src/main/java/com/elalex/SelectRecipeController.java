
package com.elalex;

import com.elalex.food.model.SelectRecipeInstructionsDB;
import com.elalex.food.model.SelectRecipeInstructionsDBRepository;
import com.elalex.food.model.SelectRecipeQueryDB;
import com.elalex.food.model.SelectRecipeQueryDBRepository;
import com.elalex.utils.CreatePdfFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

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
    @RequestMapping(method = GET, path = "/selectRecipeQuery")

    public  ResponseEntity<List<SelectRecipeQueryDB>> selectRecipeQuery(HttpServletResponse response, String recipeName, Double quantity ) {
        try {

            List<SelectRecipeQueryDB>  selectRecipeQueryDBList= selectRecipeQueryDBRepository.selectRecipeProductLinked(recipeName, quantity);

            List <SelectRecipeInstructionsDB> selectRecipeInstructionsDBList =  selectRecipeInstructionsDBRepository.selectRecipeInstructions( recipeName);

            String fileName = CreatePdfFile.createPdfFile(selectRecipeQueryDBList, selectRecipeInstructionsDBList, recipeName);
            sendEmailController.sendEmail(fileName);
            return ResponseEntity.ok(selectRecipeQueryDBList);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }


    }}