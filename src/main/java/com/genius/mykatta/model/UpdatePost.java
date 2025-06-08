package com.genius.mykatta.model;

import com.genius.mykatta.model.enums.UpdateType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer senderId;

    @Enumerated(EnumType.STRING)
    private UpdateType updateType;

    private Boolean isPinned;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private UpdatePost parent;

    private String content;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UpdatePost> replies;
}
