package ru.eugene.restest.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;

public class FileInformation {
    @JsonProperty("faculty")
    private String faculty;
    @JsonProperty("term")
    private int term;
    @JsonProperty("discipline")
    private String discipline;
    @JsonProperty("type")
    private String type;

    @JsonProperty("name")
    private String name;


    public FileInformation(String faculty, String discipline, String type, int term, String name) {
        this.faculty = faculty;
        this.discipline = discipline;
        this.type = type;
        this.term = term;
        this.name = name;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.faculty + ", " + this.discipline + ", " + this.type;
    }
}
