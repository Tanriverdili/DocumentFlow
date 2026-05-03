package com.example.api.integration.handler;
import com.example.api.dto.DocumentApprovalEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class DocumentEventHandler {
    private final MessageChannel approvalChannel;

    public DocumentEventHandler(
            @Qualifier("approvalChannel") MessageChannel approvalChannel) {
        this.approvalChannel = approvalChannel;
    }
    @EventListener
    public void handle(DocumentApprovalEvent event) {
        approvalChannel.send(
                MessageBuilder.withPayload(event).build()
        );
    }
}




