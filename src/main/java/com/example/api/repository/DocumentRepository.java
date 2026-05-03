package com.example.api.repository;
import com.example.api.entity.DocumentEntity;
import com.example.api.enums.DocumentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {
    List<DocumentEntity> findByStatus(DocumentStatus status);
    List<DocumentEntity> findAll();
    Page<DocumentEntity> findAll(Pageable pageable);
    List<DocumentEntity> findByTitleContaining(String keyword);
    long countByStatus(DocumentStatus status);
}
