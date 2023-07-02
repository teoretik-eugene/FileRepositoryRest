package ru.eugene.restest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.eugene.restest.models.Faculty;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Integer> {
    public Faculty findByNameIgnoreCase(String faculty);


}
