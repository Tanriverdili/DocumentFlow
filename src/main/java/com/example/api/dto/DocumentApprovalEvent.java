package com.example.api.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DocumentApprovalEvent {
    private Long documentId;
    private String status;
}