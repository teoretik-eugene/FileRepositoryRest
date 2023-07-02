package ru.eugene.restest.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.eugene.restest.models.Faculty;
import ru.eugene.restest.models.FileModel;
import ru.eugene.restest.models.FileTags;

import java.util.List;

@Repository
public interface FileTagsRepository extends JpaRepository<FileTags, Integer> {
    public List<FileTags> findAllByFaculty(Faculty faculty);

    @Query("select f from FileTags f left join fetch f.discipline where f.faculty.name = ?1")
    public List<FileTags> findAllByFacultyFetch(String faculty);

    public FileTags findByFileModel(FileModel fileModel);

}
