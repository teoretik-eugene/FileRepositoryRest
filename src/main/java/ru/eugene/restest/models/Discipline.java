package ru.eugene.restest.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "discipline")
public class Discipline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "discipline")
    private List<FileTags> disciplineList;

    public Discipline() {
    }

    public Discipline(int id, String name) {
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

    public List<FileTags> getDisciplineList() {
        return disciplineList;
    }

    public void setDisciplineList(List<FileTags> disciplineList) {
        this.disciplineList = disciplineList;
    }
}
