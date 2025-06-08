package com.genius.mykatta.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.genius.mykatta.model.FileRecord;
import com.genius.mykatta.model.enums.FileType;

public interface FileService {
    FileRecord uploadFile(MultipartFile file, Integer studentId, String description);
    Resource downloadFile(Integer fileId);
    List<FileRecord> getFilesByStudent(Integer studentId);
    List<FileRecord> getFilesByType(FileType fileType);
    void incrementDownloadCount(Integer fileId);
}