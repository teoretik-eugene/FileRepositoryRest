package ru.eugene.restest.models;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;


@Entity
@Table(name = "files")
public class FileModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    //TODO: добавить в БД
    @Column(name = "file_ref")
    private String fileRef;

    @OneToOne(mappedBy = "fileModel")
    private FileTags fileTags;

    public FileModel(String fileName, String filePath, String fileRef) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileRef = fileRef;
    }

    public FileModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public FileTags getFileTags() {
        return fileTags;
    }

    public void setFileTags(FileTags fileTags) {
        this.fileTags = fileTags;
    }

    @Override
    public String toString() {
        return this.fileName + " -- " + this.filePath;
    }
}
