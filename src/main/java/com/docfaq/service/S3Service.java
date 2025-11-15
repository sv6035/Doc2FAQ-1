package com.docfaq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.util.UUID;

/**
 * Service for handling Amazon S3 operations.
 */
@Service
public class S3Service {

    private final S3Client s3Client;
    private final String bucketName;

    @Autowired
    public S3Service(S3Client s3Client, @Value("${aws.s3.bucket-name}") String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    /**
     * Uploads a file to S3 and returns the unique file ID.
     *
     * @param file the multipart file to upload
     * @return the unique file ID (S3 key)
     * @throws IOException if there's an error reading the file
     * @throws S3Exception if there's an error uploading to S3
     */
    public String uploadFile(MultipartFile file) throws IOException, S3Exception {
        String fileId = generateUniqueFileId(file.getOriginalFilename());
        
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileId)
                .contentType(file.getContentType())
                .contentLength(file.getSize())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        
        return fileId;
    }

    /**
     * Generates a unique file ID based on UUID and original filename.
     *
     * @param originalFilename the original filename
     * @return a unique file ID
     */
    private String generateUniqueFileId(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String extension = "";
        
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        
        return uuid + extension;
    }
}