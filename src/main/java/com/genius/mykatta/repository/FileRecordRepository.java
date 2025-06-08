package com.genius.mykatta.repository;

import com.genius.mykatta.model.FileRecord;
import com.genius.mykatta.model.enums.FileType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FileRecordRepository extends JpaRepository<FileRecord, Integer> {

    List<FileRecord> findByStudentId(Integer studentId);

    Page<FileRecord> findByFileType(FileType fileType, Pageable pageable);

    @Query("SELECT f FROM FileRecord f WHERE " +
            "LOWER(f.fileName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(f.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<FileRecord> searchFiles(String query, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE FileRecord f SET f.downloadCount = f.downloadCount + 1 WHERE f.id = :fileId")
    void incrementDownloadCount(Integer fileId);

    @Query("SELECT COUNT(f), f.fileType FROM FileRecord f GROUP BY f.fileType")
    List<Object[]> countFilesByType();
}
