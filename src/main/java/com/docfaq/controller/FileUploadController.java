package com.docfaq.controller;

import com.docfaq.model.UploadResponse;
import com.docfaq.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller for handling file upload operations.
 */
@RestController
@RequestMapping("/api")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    /**
     * Handles file upload requests.
     *
     * @param file the multipart file to upload
     * @return ResponseEntity containing the upload result
     */
    @PostMapping("/upload")
    public ResponseEntity<UploadResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            UploadResponse response = fileUploadService.processFileUpload(file);
            
            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (Exception e) {
            UploadResponse errorResponse = new UploadResponse(false, "Internal server error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Health check endpoint for upload functionality.
     *
     * @return ResponseEntity indicating service status
     */
    @GetMapping("/upload/status")
    public ResponseEntity<String> getUploadStatus() {
        return ResponseEntity.ok("Upload service is running");
    }
}