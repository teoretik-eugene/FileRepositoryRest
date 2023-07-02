package ru.eugene.restest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.eugene.restest.DTO.FileDTO;
import ru.eugene.restest.DTO.FileInformation;
import ru.eugene.restest.JSON.LoadResponse;
import ru.eugene.restest.models.Faculty;
import ru.eugene.restest.models.FileModel;
import ru.eugene.restest.models.FileTags;
import ru.eugene.restest.repository.*;
import ru.eugene.restest.utils.FileDTOConverter;
import ru.eugene.restest.utils.StringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    private final String testPath;

    private final FileRepository fileRepository;
    private final FacultyRepository facultyRepository;
    private final DisciplineRepository disciplineRepository;
    private final FileTagsRepository fileTagsRepository;
    private final WorkTypeRepository workTypeRepository;
    private final FileDTOConverter fileDTOConverter;
    private final String startPath = "C:\\FilePathTest\\";

    @Autowired
    public FileService(FileRepository fileRepository,
                       FacultyRepository facultyRepository,
                       DisciplineRepository disciplineRepository, FileTagsRepository fileTagsRepository, WorkTypeRepository workTypeRepository, FileDTOConverter fileDTOConverter) {
        this.facultyRepository = facultyRepository;
        this.disciplineRepository = disciplineRepository;
        this.fileTagsRepository = fileTagsRepository;
        this.workTypeRepository = workTypeRepository;
        this.testPath = "C:\\FilePathTest\\AllFiles";
        this.fileRepository = fileRepository;
        this.fileDTOConverter = fileDTOConverter;
    }

    public List<FileDTO> getAllFiles(){
        return fileDTOConverter.toDTOList(fileRepository.findAll());
    }

    public Path getPathById(int id){
        FileModel model = fileRepository.getReferenceById(id);
        return Paths.get(model.getFilePath());
    }

    /*
    public LoadResponse load(MultipartFile file){
        String fileName = file.getOriginalFilename();
        String filePath = testPath + "\\" + fileName;
        System.out.println(fileName);
        try {
            file.transferTo(new File(filePath));
            FileModel model = new FileModel(fileName, filePath);
            fileRepository.save(model);
            return new LoadResponse(fileName, testPath + "\\" + fileName, file.getSize());
        }catch (IOException e){
            System.out.println("didn't receive " + e);
            return null;
        }
    }
     */

    // В этом методе получаем все файлы факультета
    public List<FileDTO> getByFaculty(String faculty){

        Faculty facultyModel = facultyRepository.findByNameIgnoreCase(faculty);
        List<FileTags> tagList = fileTagsRepository.findAllByFaculty(facultyModel);
        //List<FileTags> tagList = fileTagsRepository.findAllByFacultyFetch(faculty);
        List<FileDTO> DTOList = new ArrayList<>();

        for(FileTags tag: tagList){
            FileInformation fileInformation = new FileInformation(tag.getFaculty().getName(),
                    tag.getDiscipline().getName(), tag.getType().getName(), tag.getTerm(), tag.getName());
            FileDTO fileDTO = new FileDTO(tag.getFileModel().getFileName());
            fileDTO.setFileInformation(fileInformation);
            DTOList.add(fileDTO);
        }

        return DTOList;

    }

    /*
    TODO: сделать проверку на целостность всех значений в JSON
     */

    public boolean loadFile(MultipartFile file, FileInformation fileInformation){
        // Сделаю пока разделение по факультетам
        // Если такой факультет в запросе существует
        if(isFaculty(fileInformation.getFaculty())){
            Path path = Paths.get(startPath + fileInformation.getFaculty());
            String filePath = startPath + fileInformation.getFaculty() + "\\";
            if(Files.isDirectory(path)){
                //System.out.println("faculty exist: " + startPath + fileInformation.getFaculty());
            }
            // Если такого пути еще нет
            else {
                System.out.println("Path doesn't exist: " + startPath + fileInformation.getFaculty());
                // Пробуем создать этот путь
                try{
                    Files.createDirectory(path);

                }catch (IOException e){
                    System.out.println("cannot create a directory");
                    return false;
                }
            }
            try {
                // Сохраняем файл на диск по указанному пути
                file.transferTo(new File(filePath + file.getOriginalFilename()));
                System.out.println(filePath);
                // TODO: сделать генерацию ссылки
                FileModel fileModel = new FileModel(file.getOriginalFilename(),
                        filePath + file.getOriginalFilename(),
                        createUniqueRef(fileInformation.getName()));

                // Сохраняем модель в БД
                fileRepository.save(fileModel);
                System.out.println(fileModel);

                // Создаем сущность tags
                FileTags fileTags = new FileTags();
                fileTags.setFileModel(fileModel);

                fileTags.setFaculty(facultyRepository
                        .findByNameIgnoreCase(fileInformation.getFaculty()));

                fileTags.setDiscipline(disciplineRepository
                        .findDisciplineByNameIgnoreCase(fileInformation.getDiscipline()));

                fileTags.setTerm(fileInformation.getTerm());

                fileTags.setType(workTypeRepository
                        .findWorkTypeByNameIgnoreCase(fileInformation.getType()));

                fileTags.setName(fileInformation.getName());


                fileTagsRepository.save(fileTags);

                return true;

            } catch (IOException e) {
                return false;
            }
        }else{
            System.out.println("NOT A FACULTY");
            return false;
        }
    }

    public String createUniqueRef(String name){
        return StringConverter.uniqueReference(name);
    }

    // Проверяет есть ли указанный факультет в БД
    public boolean isFaculty(String faculty){
        if(facultyRepository.findByNameIgnoreCase(faculty) != null)
            return true;
        else
            return false;
    }

    public FileDTO getFileDTOByRef(String ref) {
        FileModel fileModel = fileRepository.findByFileRef(ref);

        if(fileModel == null)
            return null;

        FileTags fileTags = fileTagsRepository.findByFileModel(fileModel);

        FileInformation fileInformation = new FileInformation(fileTags.getFaculty().getName(),
                fileTags.getDiscipline().getName(), fileTags.getType().getName(),
                fileTags.getTerm(), fileTags.getName());

        FileDTO fileDTO = new FileDTO(fileModel.getFileName(), fileInformation);

        return fileDTO;
    }

    public Resource getFileByRef(String ref){
        FileModel fileModel = fileRepository.findByFileRef(ref);
        if(fileModel == null)
            return null;

        String name = fileModel.getFileName();
        Path path = Paths.get(fileModel.getFilePath());
        try {

            ByteArrayResource fileResource = new ByteArrayResource(Files.readAllBytes(path));
            //Resource fileResource = new UrlResource(path.toUri());
            //System.out.println(fileResource.getFilename());
            return fileResource;
        } catch (IOException e) {
            return null;
        }
    }

    // Проверка существует ли такая ссылка на файл
    public boolean referenceIsExist(String ref){
        if(fileRepository.findByFileRef(ref) != null)
            return true;
        else
            return false;
    }

    public String getFileName(String ref){
        return StringConverter.toEnglish(fileRepository.findByFileRef(ref).getFileName());
    }
}
