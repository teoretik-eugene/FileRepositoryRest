package ru.eugene.restest.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "term")
public class Term {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "term")
    private int termNumber;

    /*
    @OneToMany(mappedBy = "term")
    private List<FileTags> termList;*/

    public Term() {
    }

    public Term(int id, int termNumber) {
        this.id = id;
        this.termNumber = termNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTermNumber() {
        return termNumber;
    }

    public void setTermNumber(int termNumber) {
        this.termNumber = termNumber;
    }

}
