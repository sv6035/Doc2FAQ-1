package com.docfaq.service;

import com.docfaq.model.UploadResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit tests for FileUploadService.
 */
@ExtendWith(MockitoExtension.class)
class FileUploadServiceTest {

    @Mock
    private S3Service s3Service;

    @InjectMocks
    private FileUploadService fileUploadService;

    private MultipartFile validPdfFile;
    private MultipartFile validDocxFile;
    private MultipartFile validTxtFile;
    private MultipartFile invalidFile;
    private MultipartFile oversizedFile;

    @BeforeEach
    void setUp() {
        // Create valid test files
        validPdfFile = new MockMultipartFile(
                "file", 
                "test.pdf", 
                "application/pdf", 
                "test content".getBytes()
        );

        validDocxFile = new MockMultipartFile(
                "file", 
                "test.docx", 
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document", 
                "test content".getBytes()
        );

        validTxtFile = new MockMultipartFile(
                "file", 
                "test.txt", 
                "text/plain", 
                "test content".getBytes()
        );

        // Create invalid file type
        invalidFile = new MockMultipartFile(
                "file", 
                "test.jpg", 
                "image/jpeg", 
                "test content".getBytes()
        );

        // Create oversized file (11 MB)
        byte[] largeContent = new byte[11 * 1024 * 1024];
        oversizedFile = new MockMultipartFile(
                "file", 
                "large.pdf", 
                "application/pdf", 
                largeContent
        );
    }

    @Test
    void processFileUpload_ValidPdfFile_ShouldSucceed() throws Exception {
        // Arrange
        String expectedFileId = "test-uuid.pdf";
        when(s3Service.uploadFile(any(MultipartFile.class))).thenReturn(expectedFileId);

        // Act
        UploadResponse response = fileUploadService.processFileUpload(validPdfFile);

        // Assert
        assertTrue(response.isSuccess());
        assertEquals("File uploaded successfully", response.getMessage());
        assertEquals(expectedFileId, response.getFileId());
        assertEquals("test.pdf", response.getFileName());
        assertEquals(validPdfFile.getSize(), response.getFileSize());
    }

    @Test
    void processFileUpload_ValidDocxFile_ShouldSucceed() throws Exception {
        // Arrange
        String expectedFileId = "test-uuid.docx";
        when(s3Service.uploadFile(any(MultipartFile.class))).thenReturn(expectedFileId);

        // Act
        UploadResponse response = fileUploadService.processFileUpload(validDocxFile);

        // Assert
        assertTrue(response.isSuccess());
        assertEquals("File uploaded successfully", response.getMessage());
        assertEquals(expectedFileId, response.getFileId());
        assertEquals("test.docx", response.getFileName());
    }

    @Test
    void processFileUpload_ValidTxtFile_ShouldSucceed() throws Exception {
        // Arrange
        String expectedFileId = "test-uuid.txt";
        when(s3Service.uploadFile(any(MultipartFile.class))).thenReturn(expectedFileId);

        // Act
        UploadResponse response = fileUploadService.processFileUpload(validTxtFile);

        // Assert
        assertTrue(response.isSuccess());
        assertEquals("File uploaded successfully", response.getMessage());
        assertEquals(expectedFileId, response.getFileId());
        assertEquals("test.txt", response.getFileName());
    }

    @Test
    void processFileUpload_InvalidFileType_ShouldFail() {
        // Act
        UploadResponse response = fileUploadService.processFileUpload(invalidFile);

        // Assert
        assertFalse(response.isSuccess());
        assertEquals("Invalid file type. Only PDF, DOCX, and TXT files are allowed", response.getMessage());
        assertNull(response.getFileId());
    }

    @Test
    void processFileUpload_OversizedFile_ShouldFail() {
        // Act
        UploadResponse response = fileUploadService.processFileUpload(oversizedFile);

        // Assert
        assertFalse(response.isSuccess());
        assertEquals("File size exceeds maximum limit of 10 MB", response.getMessage());
        assertNull(response.getFileId());
    }

    @Test
    void processFileUpload_NullFile_ShouldFail() {
        // Act
        UploadResponse response = fileUploadService.processFileUpload(null);

        // Assert
        assertFalse(response.isSuccess());
        assertEquals("Please select a file to upload", response.getMessage());
        assertNull(response.getFileId());
    }

    @Test
    void processFileUpload_EmptyFile_ShouldFail() {
        // Arrange
        MultipartFile emptyFile = new MockMultipartFile(
                "file", 
                "empty.pdf", 
                "application/pdf", 
                new byte[0]
        );

        // Act
        UploadResponse response = fileUploadService.processFileUpload(emptyFile);

        // Assert
        assertFalse(response.isSuccess());
        assertEquals("Please select a file to upload", response.getMessage());
        assertNull(response.getFileId());
    }

    @Test
    void processFileUpload_S3UploadFailure_ShouldReturnError() throws Exception {
        // Arrange
        when(s3Service.uploadFile(any(MultipartFile.class)))
                .thenThrow(new RuntimeException("S3 connection failed"));

        // Act
        UploadResponse response = fileUploadService.processFileUpload(validPdfFile);

        // Assert
        assertFalse(response.isSuccess());
        assertTrue(response.getMessage().contains("Upload failed"));
        assertNull(response.getFileId());
    }
}