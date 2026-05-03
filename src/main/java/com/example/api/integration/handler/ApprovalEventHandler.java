package com.example.api.integration.handler;
import com.example.api.dto.DocumentApprovalEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApprovalEventHandler {
    private final MessageChannel approvalChannel;

    @EventListener
    public void handle(DocumentApprovalEvent event) {
        approvalChannel.send(
                MessageBuilder.withPayload(event).build()
        );
    }
}
