package com.example.api.notification;
import com.example.api.dto.DocumentApprovalEvent;
import com.example.api.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {
    private final NotificationService notificationService;

    @EventListener
    public void onApproval(DocumentApprovalEvent event) {
        notificationService.sendUpdate(
                event.getDocumentId(),
                event.getStatus()
        );
    }
}