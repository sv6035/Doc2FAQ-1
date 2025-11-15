package com.docfaq.service;

import com.docfaq.model.UploadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Service for handling file upload operations and validation.
 */
@Service
public class FileUploadService {

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("pdf", "docx", "txt");
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10 MB in bytes

    @Autowired
    private S3Service s3Service;

    /**
     * Processes file upload with validation and S3 storage.
     *
     * @param file the multipart file to upload
     * @return UploadResponse containing the result of the upload operation
     */
    public UploadResponse processFileUpload(MultipartFile file) {
        try {
            // Validate file
            UploadResponse validationResult = validateFile(file);
            if (!validationResult.isSuccess()) {
                return validationResult;
            }

            // Upload to S3
            String fileId = s3Service.uploadFile(file);

            return new UploadResponse(
                true,
                "File uploaded successfully",
                fileId,
                file.getOriginalFilename(),
                file.getSize()
            );

        } catch (IOException e) {
            return new UploadResponse(false, "Error reading file: " + e.getMessage());
        } catch (Exception e) {
            return new UploadResponse(false, "Upload failed: " + e.getMessage());
        }
    }

    /**
     * Validates the uploaded file for type and size constraints.
     *
     * @param file the multipart file to validate
     * @return UploadResponse indicating validation result
     */
    private UploadResponse validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return new UploadResponse(false, "Please select a file to upload");
        }

        // Check file size
        if (file.getSize() > MAX_FILE_SIZE) {
            return new UploadResponse(false, "File size exceeds maximum limit of 10 MB");
        }

        // Check file extension
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !hasValidExtension(originalFilename)) {
            return new UploadResponse(false, "Invalid file type. Only PDF, DOCX, and TXT files are allowed");
        }

        return new UploadResponse(true, "File validation passed");
    }

    /**
     * Checks if the file has a valid extension.
     *
     * @param filename the filename to check
     * @return true if the extension is valid, false otherwise
     */
    private boolean hasValidExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return false;
        }

        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        return ALLOWED_EXTENSIONS.contains(extension);
    }
}