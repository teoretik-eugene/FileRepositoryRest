package ru.eugene.restest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.eugene.restest.models.WorkType;

@Repository
public interface WorkTypeRepository extends JpaRepository<WorkType, Integer> {
    public WorkType findWorkTypeByNameIgnoreCase(String workType);
}
