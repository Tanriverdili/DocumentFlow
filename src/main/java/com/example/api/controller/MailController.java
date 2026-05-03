package com.example.api.controller;
import com.example.api.dto.EmailMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {
    private final MessageChannel mailChannel;

    @PostMapping("/send")
    public String send(@RequestBody EmailMessage email) {
        mailChannel.send(
                MessageBuilder.withPayload(email).build()
        );
        return "Mail flow triggered";
    }
}