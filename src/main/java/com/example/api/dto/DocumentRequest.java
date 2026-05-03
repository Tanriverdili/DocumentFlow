package com.example.api.dto;
import lombok.*;

@Data
@AllArgsConstructor
@Getter
@Setter
public class DocumentRequest {
    private String fileUrl;
    private String description;
    private String title;
}

