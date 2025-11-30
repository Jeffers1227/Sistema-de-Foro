package com.example.forum.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data @NoArgsConstructor
@Table(name = "comments")
public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El comentario no puede estar vacío")
    // CAMBIO: LONGTEXT soporta hasta 4GB (necesario para imágenes en Base64)
    @Column(columnDefinition = "LONGTEXT")
    private String text;

    private boolean validated = false; // Solo el autor del foro puede poner esto en true

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forum_id")
    private Forum forum;

    @CreationTimestamp
    private LocalDateTime createdAt;
}