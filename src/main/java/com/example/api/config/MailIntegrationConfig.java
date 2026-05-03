package com.example.api.config;
import com.example.api.dto.EmailMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import java.util.Properties;

@Configuration
public class MailIntegrationConfig {
    @Bean
    public MessageChannel mailChannel() {
        return new DirectChannel();
    }
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("docflow.api.system2026@gmail.com");
        mailSender.setPassword("qrvtfpspazltuyfz");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        props.put("mail.smtp.ssl.trust", "*");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        return mailSender;
    }
    @Bean
    @ServiceActivator(inputChannel = "mailChannel")
    public MessageHandler mailHandler(JavaMailSender mailSender) {

        return message -> {
            EmailMessage email = (EmailMessage) message.getPayload();
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(email.getTo());
            mail.setSubject(email.getSubject());
            mail.setText(email.getBody());
            mail.setFrom("docflow.api.system2026@gmail.com");

            mailSender.send(mail);

            System.out.println(" Email successfully sent to: " + email.getTo());
        };
    }
}