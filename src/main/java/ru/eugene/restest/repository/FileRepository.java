package ru.eugene.restest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.eugene.restest.models.FileModel;

@Repository
public interface FileRepository extends JpaRepository<FileModel, Integer> {

    public FileModel findByFileRef(String ref);

}
