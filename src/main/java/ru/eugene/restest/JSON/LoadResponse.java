package ru.eugene.restest.JSON;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoadResponse {
    @JsonProperty("file-name")
    private String fileName;
    @JsonProperty("file-path")
    private String filePath;
    @JsonProperty("file-size")
    private long size;

    public LoadResponse(String fileName, String filePath, long size) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.size = size;
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

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
