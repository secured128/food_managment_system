package com.elalex;

import com.elalex.utils.CreatePdfFile;
import com.elalex.utils.Excel2String;
import com.elalex.utils.GeneralUtils;
import com.elalex.food.model.SupplierDB;
import com.elalex.food.model.SupplierDBRepository;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * https://spring.io/guides/tutorials/bookmarks/
 */
@RestController
public class FileDownloadController {


    @RequestMapping(value="download", method=RequestMethod.GET)
    public void getDownload(HttpServletResponse response) {

// Get your file stream from wherever.
        File file = new File("pdfBoxHelloWorld.pdf");

        try
        {
            InputStream myStream =  new URL(file.toURI().toString()).openStream();
// Set the content type and attachment header.
        response.addHeader("Content-disposition", "attachment;filename=pdfBoxHelloWorld.pdf");
        response.setContentType("application/pdf");

// Copy the stream to the response's output stream.
        IOUtils.copy(myStream, response.getOutputStream());
        response.flushBuffer();
        }
        catch (Exception e) {

        }


    }
}