package com.example.api.controller;
import com.example.api.dto.DashboardResponse;
import com.example.api.dto.DocumentRequest;
import com.example.api.dto.DocumentResponse;
import com.example.api.entity.AuditEntity;
import com.example.api.entity.DocumentEntity;
import com.example.api.enums.DocumentStatus;
import com.example.api.repository.DocumentRepository;
import com.example.api.service.AuditService;
import com.example.api.service.DocumentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name="Document API")
@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;
    private final DocumentRepository documentRepository;
    private final AuditService auditService;

    @PostMapping("/submit")
    public DocumentResponse submit(@RequestBody DocumentRequest request) {
        return documentService.submit(request);
    }
    @PostMapping("/{id}/approve")
    public ResponseEntity<String> approve(@PathVariable Long id) {
        documentService.approve(id);
        return ResponseEntity.ok("Approved successfully");
    }
    @PostMapping("/{id}/reject")
    public ResponseEntity<String> reject(@PathVariable Long id) {
        documentService.reject(id);
        return ResponseEntity.ok("Rejected successfully");
    }
    @PostMapping("/{id}/archive")
    public DocumentResponse archive(@PathVariable Long id) {
        return documentService.archive(id);
    }
    @GetMapping("/status/{status}")
    public List<DocumentEntity> getByStatus(@PathVariable DocumentStatus status) {
        return documentService.getByStatus(status);
    }
    @GetMapping("/all")
    public Page<DocumentEntity> getAll(
            @RequestParam int page,
            @RequestParam int size) {

        return documentService.getAll(page, size);
    }
    @GetMapping("/{id}/history")
    public List<AuditEntity> history(@PathVariable Long id) {
        return auditService.getByDocumentId(id);
    }

    @GetMapping("/dashboard")
    public DashboardResponse getDashboard() {
        return documentService.getDashboard();
    }
}








