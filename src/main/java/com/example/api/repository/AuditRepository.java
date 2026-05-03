package com.example.api.repository;
import com.example.api.entity.AuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AuditRepository extends JpaRepository<AuditEntity, Long> {
    List<AuditEntity> findByDocumentId(Long documentId);
    List<AuditEntity> findAllByOrderByTimestampDesc();
}