package ru.eugene.restest.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/pdf")
public class PDFController {

    @GetMapping()
    public String hello(){
        return "hello pdf";
    }

    @GetMapping(value = "/test_pdf")
    public ResponseEntity<?> getTestPDF(){
        Path path = Paths.get("C:\\FilePathTest\\TestFiles\\spec.pdf");
        try {
            byte[] pdf = Files.readAllBytes(path);
            String filename = "out.pdf";
            HttpHeaders headers = new HttpHeaders();
            //headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", filename);
            return new ResponseEntity<>(pdf, headers, HttpStatus.OK);

        }catch (IOException e){
            System.out.println("can not read file");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
