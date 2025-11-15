package com.docfaq.model;

/**
 * Response model for file upload operations.
 */
public class UploadResponse {
    private boolean success;
    private String message;
    private String fileId;
    private String fileName;
    private long fileSize;

    public UploadResponse() {}

    public UploadResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public UploadResponse(boolean success, String message, String fileId, String fileName, long fileSize) {
        this.success = success;
        this.message = message;
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileSize = fileSize;
    }

    // Getters and setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
}