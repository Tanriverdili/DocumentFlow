package com.example.api.audit.listener;
import com.example.api.dto.DocumentApprovalEvent;
import com.example.api.dto.DocumentSubmittedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DocumentEventListener {
    private final MessageChannel documentChannel;
    private final MessageChannel approvalChannel;

    @EventListener
    public void handleSubmit(DocumentSubmittedEvent event) {
        documentChannel.send(
                MessageBuilder.withPayload(event.getId()).build()
        );
    }
    @EventListener
    public void handleApproval(DocumentApprovalEvent event) {
        approvalChannel.send(
                MessageBuilder.withPayload(event).build()
        );
    }
}