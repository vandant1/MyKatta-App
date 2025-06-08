package com.genius.mykatta.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.genius.mykatta.exception.ResourceNotFoundException;
import com.genius.mykatta.model.FileRecord;
import com.genius.mykatta.model.Student;
import com.genius.mykatta.model.enums.FileType;
import com.genius.mykatta.repository.FileRecordRepository;
import com.genius.mykatta.repository.StudentRepository;
import com.genius.mykatta.service.FileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRecordRepository fileRecordRepository;
    private final StudentRepository studentRepository;

    private final String UPLOAD_DIR = "uploaded_files/";

    @Override
    @Transactional
    public FileRecord uploadFile(MultipartFile file, Integer studentId, String description) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
        } catch (IOException e) {
            throw new RuntimeException("Failed to create upload directory", e);
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);

        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }

        String extension = getFileExtension(file.getOriginalFilename());
        FileType fileType;
        try {
            fileType = FileType.valueOf(extension.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Unsupported file type: " + extension);
        }

        FileRecord fileRecord = FileRecord.builder()
                .student(student)
                .fileName(fileName)
                .fileType(fileType)
                .fileSize(file.getSize())
                .filePath(filePath.toString())
                .description(description)
                .build();

        return fileRecordRepository.save(fileRecord);
    }

    @Override
    public Resource downloadFile(Integer fileId) {
        FileRecord fileRecord = fileRecordRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("File not found"));

        File file = new File(fileRecord.getFilePath());
        if (!file.exists()) {
            throw new ResourceNotFoundException("File not found on disk");
        }
        return new FileSystemResource(file);
    }

    @Override
    public List<FileRecord> getFilesByStudent(Integer studentId) {
        return fileRecordRepository.findByStudentId(studentId);
    }

    @Override
    public List<FileRecord> getFilesByType(FileType fileType) {
        return fileRecordRepository.findByFileType(fileType, PageRequest.of(0, 100)).getContent();
    }

    @Override
    @Transactional
    public void incrementDownloadCount(Integer fileId) {
        fileRecordRepository.incrementDownloadCount(fileId);
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            throw new RuntimeException("Invalid file name");
        }
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }
}
