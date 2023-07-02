package ru.eugene.restest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.eugene.restest.models.Discipline;

@Repository
public interface DisciplineRepository extends JpaRepository<Discipline, Integer> {

    public Discipline findDisciplineByNameIgnoreCase(String discipline);
}
