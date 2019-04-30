package com.elalex;

import com.elalex.utils.ProfileManager;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.BaseFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    public static BaseFont bf;
    public static String recipesPath = "./src/main/resources/RecipePdf";
@Autowired
  static  ProfileManager profileManager;


    public static void main(String[] args) throws IOException {

        SpringApplication.run(Application.class, args);
        String path = "arialuni.ttf";
        FontFactory.register(path);
        try {
            bf = BaseFont.createFont(path, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        File recipeDir = new File(recipesPath);
        if (recipeDir.exists()) {
            for (File f : recipeDir.listFiles()) {
                if (f.getName().startsWith("recipe")) {
                    f.delete();
                }
            }
        } else {
            recipeDir.mkdir();
        }


        logger.info("******************************************************************");
        logger.info("****************Food management application is Up and Running************");
        logger.info("******************************************************************");


    }
}


