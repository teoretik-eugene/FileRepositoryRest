package ru.eugene.restest.DTO;

public class FileDTO {

    private String fileName;

    private FileInformation fileInformation;

    public FileDTO() {
    }

    public FileDTO(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public FileInformation getFileInformation() {
        return fileInformation;
    }

    public FileDTO(String fileName, FileInformation fileInformation){
        this.fileName = fileName;
        this.fileInformation = fileInformation;
    }

    public void setFileInformation(FileInformation fileInformation) {
        this.fileInformation = fileInformation;
    }
}
