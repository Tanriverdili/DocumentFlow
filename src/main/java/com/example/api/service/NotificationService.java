package com.example.api.service;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final SimpMessagingTemplate messagingTemplate;

    public void sendUpdate(Long docId, String status) {
      messagingTemplate.convertAndSend("/topic/documents", "Document " + docId + " is " + status);
    }
}
