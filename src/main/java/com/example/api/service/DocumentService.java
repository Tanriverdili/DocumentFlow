package com.example.api.service;
import com.example.api.dto.*;
import com.example.api.entity.DocumentEntity;
import com.example.api.enums.DocumentStatus;
import com.example.api.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.integration.support.MessageBuilder;
import com.example.api.dto.DashboardResponse;
import com.example.api.dto.EmailMessage;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final MessageChannel mailChannel;
    private final AuditService auditService;

    private static final String SYSTEM_USER = "system";
    private static final String ADMIN_USER = "admin";
    private static final String DEFAULT_EMAIL = "test@gmail.com";

    public DashboardResponse getDashboard() {
        return new DashboardResponse(
                documentRepository.countByStatus(DocumentStatus.APPROVED),
                documentRepository.countByStatus(DocumentStatus.REJECTED),
                documentRepository.countByStatus(DocumentStatus.ARCHIVED),
                documentRepository.countByStatus(DocumentStatus.PENDING_APPROVAL)
        );
    }
    private void sendEmail(String to, String subject, String body) {
        EmailMessage email = new EmailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setBody(body);

        mailChannel.send(
                MessageBuilder.withPayload(email).build()
        );
    }
    public List<DocumentEntity> getByStatus(DocumentStatus status) {
        return documentRepository.findByStatus(status);
    }
    public Page<DocumentEntity> getAll(int page, int size) {
        return documentRepository.findAll(PageRequest.of(page, size));
    }
    public DocumentResponse submit(DocumentRequest request) {
        DocumentEntity document = DocumentEntity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .fileUrl(request.getFileUrl())
                .status(DocumentStatus.PENDING_APPROVAL)
                .build();

        DocumentEntity saved = documentRepository.save(document);

        auditService.log(
                saved.getId(),
                "SUBMIT",
                "Document submitted",
                SYSTEM_USER
        );

        return mapToResponse(saved, "Document submitted successfully");
    }
    public void approve(Long id) {
        DocumentEntity document = getDocument(id);
        document.setStatus(DocumentStatus.APPROVED);

        documentRepository.save(document);

        auditAndNotify(id, "APPROVE", "Document approved", "Document Approved");
    }

    public void reject(Long id) {
        DocumentEntity document = getDocument(id);
        document.setStatus(DocumentStatus.REJECTED);
        documentRepository.save(document);

        auditAndNotify(id, "REJECT", "Document rejected", "Document Rejected");
    }
    public DocumentResponse archive(Long id) {
        DocumentEntity document = getDocument(id);

        if (document.getStatus() != DocumentStatus.APPROVED) {
            throw new RuntimeException("Only approved documents can be archived");
        }
        document.setStatus(DocumentStatus.ARCHIVED);
        DocumentEntity saved = documentRepository.save(document);

        auditService.log(
                id,
                "ARCHIVE",
                "Document archived",
                ADMIN_USER
        );

        sendEmail(
                DEFAULT_EMAIL,
                "Document Archived",
                "Document ID " + id + " has been archived"
        );

        return mapToResponse(saved, "Document archived successfully");
    }
    private void validateTransition(DocumentStatus current, DocumentStatus target) {
        if (current == DocumentStatus.ARCHIVED) {
            throw new RuntimeException("Archived document cannot be changed");
        }
        if (current == DocumentStatus.REJECTED) {
            throw new RuntimeException("Rejected document cannot be changed");
        }
        if (current == DocumentStatus.PENDING_APPROVAL &&
                !(target == DocumentStatus.APPROVED || target == DocumentStatus.REJECTED)) {
            throw new RuntimeException("Invalid transition from PENDING");
        }
        if (current == DocumentStatus.APPROVED &&
                target != DocumentStatus.ARCHIVED) {
            throw new RuntimeException("Approved can only go to ARCHIVED");
        }
    }
    private DocumentEntity getDocument(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));
    }
    private void auditAndNotify(Long id, String action, String auditMessage, String emailSubject) {
        auditService.log(
                id,
                action,
                auditMessage,
                ADMIN_USER
        );

        sendEmail(
                DEFAULT_EMAIL,
                emailSubject,
                "Document ID " + id + " has been " + action.toLowerCase()
        );

        System.out.println(action + " + EMAIL");
    }
    private DocumentResponse mapToResponse(DocumentEntity saved, String message) {
        return new DocumentResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getStatus().name(),
                message
        );
    }
}




















