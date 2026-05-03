package com.example.api.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class DocumentResponse {
    private Long id;
    private String title;
    private String status;
    private String message;
}