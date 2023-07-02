package ru.eugene.restest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.eugene.restest.DTO.FileDTO;
import ru.eugene.restest.DTO.FileInformation;
import ru.eugene.restest.JSON.RefTest;
import ru.eugene.restest.service.FileService;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;
    private final ObjectMapper objectMapper;

    @Autowired
    public FileController(FileService fileService, ObjectMapper objectMapper) {
        this.fileService = fileService;
        this.objectMapper = objectMapper;
    }

    // TODO: Доработать этот метод, возвращает пустую инфу

    @GetMapping()
    public List<FileDTO> getAll(){
        return fileService.getAllFiles();
    }

    @Deprecated
    public ResponseEntity<?> getFileById(@PathVariable("id") int id){

        try{
            byte[] byteFile = Files.readAllBytes(fileService.getPathById(id));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(byteFile);

        }catch (IOException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*
    @PostMapping("/upload")
    public LoadResponse uploadFile(@RequestParam("file")MultipartFile file){
        if(file.isEmpty()){
            return null;
        }else{
            LoadResponse loadResponse = fileService.load(file);
            try {
                String jsonResponse = objectMapper.writeValueAsString(loadResponse);
                return loadResponse;
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
     */

    // POST запрос для загрузки файла
    @PostMapping("/load")
    public ResponseEntity<?> loadFile(@RequestParam("file") MultipartFile file,
            @RequestParam("file_information") String information){
        System.out.println(information);
        System.out.println(file.getOriginalFilename());

        try {
            FileInformation fileInformation = objectMapper
                    .readValue(information, FileInformation.class);
            System.out.println(fileInformation);

            if(!fileService.loadFile(file, fileInformation))
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            return new ResponseEntity<>(fileInformation, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            System.out.println("ERROR IN JSON");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/faculty")
    public List<FileDTO> getFilesByFaculty(@RequestParam(name = "name") String faculty){
        return fileService.getByFaculty(faculty);
    }

    // Тестовый метод для проверки
    @GetMapping("/ref")
    public String refTest(@RequestBody RefTest refTest){
        return fileService.createUniqueRef(refTest.getRef());
    }

    @GetMapping("/info/{reference}")
    public ResponseEntity<?> getFileInfoByRef(@PathVariable("reference") String ref){

        FileDTO fileDTO = fileService.getFileDTOByRef(ref);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if(fileDTO == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(fileDTO, headers, HttpStatus.OK);
    }

    // TODO: сделать возврат файла через запрос
    @GetMapping("/{reference}")
    public ResponseEntity<?> getFileByRef(@PathVariable("reference") String reference){

        Resource fileResource = fileService.getFileByRef(reference);
        if(fileResource == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HttpHeaders headers = new HttpHeaders();

        // no filename??
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename(fileService.getFileName(reference))
                .build());

        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
                .headers(headers)
                .body(fileResource);
    }

}

//      Задачи

/*
    + TODO: сделать уникальную ссылку на файл, чтобы получать его не по ID, а по уникальной ссылке
    + TODO: сделать скачивание файла по запросу
    TODO: сделать выброс исключения при неправильной ссылке или запросе
    TODO: сделать запрос PATCH - подумай как именно
    TODO: Метод /files возвращает пустую инфу
    TODO: Возвращать в информации ссылку??

    // Вопросы:
    какие вообще заголовки необходимо устанавливать в ответ?

 */
