package com.example.api.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DocumentSubmittedEvent {
    private Long documentId;
    private String title;
    private String description;

    public Object getId() {
        return null;
    }
}
