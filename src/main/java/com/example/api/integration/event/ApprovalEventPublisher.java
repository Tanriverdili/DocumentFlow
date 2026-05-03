package com.example.api.integration.event;
import com.example.api.dto.DocumentApprovalEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApprovalEventPublisher {
    private final ApplicationEventPublisher publisher;

    public void publish(Long id, String status) {
        publisher.publishEvent(
                new DocumentApprovalEvent(id, status)
        );
    }
}
