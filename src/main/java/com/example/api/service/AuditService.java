package com.example.api.service;
import com.example.api.entity.AuditEntity;
import com.example.api.repository.AuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditService {
    private final AuditRepository auditRepository;

    public void log(Long docId, String action, String details, String username) {
        AuditEntity log = new AuditEntity();
        log.setDocumentId(docId);
        log.setAction(action);
        log.setDetails(details);
        log.setUsername(username);
        log.setTimestamp(LocalDateTime.now());

        auditRepository.save(log);
    }
    public List<AuditEntity> getAll() {
        return auditRepository.findAllByOrderByTimestampDesc();
    }
    public List<AuditEntity> getByDocumentId(Long id) {
        return auditRepository.findByDocumentId(id);
    }
}