package com.example.todomysql.repository;

import com.example.todomysql.entity.Todo;
import com.example.todomysql.entity.TodoStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Spring Data JPA repository for Todo entities
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    // Find todos by status
    Page<Todo> findByStatus(TodoStatus status, Pageable pageable);
    
    // Search todos by title containing the given text (case-insensitive)
    Page<Todo> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    
    // Find all with pagination
    @Override
    Page<Todo> findAll(Pageable pageable);
} 