package com.example.todomysql.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

// To-do entity mapped to "todos" table
@Data
@Entity
@Table(name = "todos")
public class Todo {
    // Primary key (auto-increment)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Title (required)
    @Column(nullable = false)
    private String title;

    // Detailed description (optional)
    @Column(columnDefinition = "TEXT")
    private String description;

    // Current status
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TodoStatus status;

    // Creation timestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
} 