package ru.eugene.restest.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "type_of_work")
public class WorkType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "type")
    private List<FileTags> typeList;

    public WorkType() {
    }

    public WorkType(int id, String name) {
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

    public List<FileTags> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<FileTags> typeList) {
        this.typeList = typeList;
    }
}
