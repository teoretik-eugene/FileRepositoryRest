package ru.eugene.restest.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "faculty")
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "faculty")
    private List<FileTags> facultyList;

    public Faculty() {
    }

    public Faculty(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FileTags> getFacultyList() {
        return facultyList;
    }

    public void setFacultyList(List<FileTags> facultyList) {
        this.facultyList = facultyList;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
