package com.genius.mykatta.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer adminId;

    private String action;

    private LocalDateTime actionTime;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Builder.Default
    private Boolean isCurrent = false;
}
