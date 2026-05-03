package com.example.api.integration.event;
import com.example.api.dto.DocumentSubmittedEvent;
import com.example.api.entity.DocumentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DocumentEventPublisher {
    private final ApplicationEventPublisher publisher;

    public void publishDocumentSubmitted(DocumentEntity doc) {
        publisher.publishEvent(
                new DocumentSubmittedEvent(
                        doc.getId(),
                        doc.getTitle(),
                        doc.getDescription()
                )
        );
    }
}
