package com.example.api.audit.listener;
import com.example.api.dto.DocumentApprovalEvent;
import com.example.api.dto.DocumentSubmittedEvent;
import com.example.api.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuditEventListener {
    private final AuditService auditService;

    @EventListener
    public void onDocumentSubmitted(DocumentSubmittedEvent event) {
        auditService.log(
                event.getDocumentId(),
                "SUBMITTED",
                "Document created",
                "system"
        );
    }
    @EventListener
    public void onDocumentApproved(DocumentApprovalEvent event) {
        auditService.log(
                event.getDocumentId(),
                event.getStatus(),
                "Status changed",
                "approve"
        );
    }
}
