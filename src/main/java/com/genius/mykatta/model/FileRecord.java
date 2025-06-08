package com.genius.mykatta.model;

import com.genius.mykatta.model.enums.FileType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "files", indexes = @Index(name = "idx_file_type", columnList = "fileType"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(nullable = false, length = 255)
    private String fileName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private FileType fileType;

    @Column(nullable = false)
    private Long fileSize;

    @Column(nullable = false, length = 500)
    private String filePath;

    @Column(length = 500)
    private String description;

    @CreationTimestamp
    private LocalDateTime uploadDate;

    @Builder.Default
    private Integer downloadCount = 0;

    public void incrementDownloadCount() {
        this.downloadCount++;
    }
}
