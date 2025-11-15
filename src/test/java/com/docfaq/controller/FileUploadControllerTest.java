package com.docfaq.controller;

import com.docfaq.model.UploadResponse;
import com.docfaq.service.FileUploadService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for FileUploadController.
 */
@WebMvcTest(FileUploadController.class)
class FileUploadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileUploadService fileUploadService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void uploadFile_ValidFile_ShouldReturnSuccess() throws Exception {
        // Arrange
        MockMultipartFile file = new MockMultipartFile(
                "file", 
                "test.pdf", 
                "application/pdf", 
                "test content".getBytes()
        );

        UploadResponse successResponse = new UploadResponse(
                true, 
                "File uploaded successfully", 
                "test-uuid.pdf", 
                "test.pdf", 
                file.getSize()
        );

        when(fileUploadService.processFileUpload(any())).thenReturn(successResponse);

        // Act & Assert
        mockMvc.perform(multipart("/api/upload")
                .file(file))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("File uploaded successfully"))
                .andExpect(jsonPath("$.fileId").value("test-uuid.pdf"))
                .andExpect(jsonPath("$.fileName").value("test.pdf"))
                .andExpect(jsonPath("$.fileSize").value(file.getSize()));
    }

    @Test
    void uploadFile_InvalidFile_ShouldReturnBadRequest() throws Exception {
        // Arrange
        MockMultipartFile file = new MockMultipartFile(
                "file", 
                "test.jpg", 
                "image/jpeg", 
                "test content".getBytes()
        );

        UploadResponse errorResponse = new UploadResponse(
                false, 
                "Invalid file type. Only PDF, DOCX, and TXT files are allowed"
        );

        when(fileUploadService.processFileUpload(any())).thenReturn(errorResponse);

        // Act & Assert
        mockMvc.perform(multipart("/api/upload")
                .file(file))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Invalid file type. Only PDF, DOCX, and TXT files are allowed"));
    }

    @Test
    void uploadFile_ServiceException_ShouldReturnInternalServerError() throws Exception {
        // Arrange
        MockMultipartFile file = new MockMultipartFile(
                "file", 
                "test.pdf", 
                "application/pdf", 
                "test content".getBytes()
        );

        when(fileUploadService.processFileUpload(any())).thenThrow(new RuntimeException("Service error"));

        // Act & Assert
        mockMvc.perform(multipart("/api/upload")
                .file(file))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Internal server error: Service error"));
    }

    @Test
    void getUploadStatus_ShouldReturnOk() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/upload/status"))
                .andExpect(status().isOk())
                .andExpect(content().string("Upload service is running"));
    }
}