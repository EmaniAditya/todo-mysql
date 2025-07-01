package com.example.todomysql.service;

import com.example.todomysql.entity.Todo;
import com.example.todomysql.entity.TodoStatus;
import com.example.todomysql.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

// Business logic for Todo items
@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    // Retrieve all to-do items (legacy method)
    public List<Todo> findAll() {
        return todoRepository.findAll();
    }
    
    // Retrieve all to-do items with pagination and sorting
    public Page<Todo> findAll(Pageable pageable) {
        return todoRepository.findAll(pageable);
    }
    
    // Find todos by status with pagination and sorting
    public Page<Todo> findByStatus(TodoStatus status, Pageable pageable) {
        return todoRepository.findByStatus(status, pageable);
    }
    
    // Search todos by title with pagination and sorting
    public Page<Todo> searchByTitle(String title, Pageable pageable) {
        return todoRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

    // Get one to-do by id
    public Todo get(Long id) {
        return todoRepository.findById(id).orElse(null);
    }

    // Create new to-do (defaults status to TODO)
    @Transactional
    public Todo create(Todo todo) {
        if (todo.getStatus() == null) {
            todo.setStatus(TodoStatus.TODO);
        }
        todo.setCreatedAt(LocalDateTime.now());
        return todoRepository.save(todo);
    }

    // Update an existing to-do
    @Transactional
    public Todo update(Long id, Todo patch) {
        return todoRepository.findById(id).map(existing -> {
            if (patch.getTitle() != null) {
                existing.setTitle(patch.getTitle());
            }
            if (patch.getDescription() != null) {
                existing.setDescription(patch.getDescription());
            }
            if (patch.getStatus() != null) {
                existing.setStatus(patch.getStatus());
            }
            return todoRepository.save(existing);
        }).orElse(null);
    }

    // Delete by id – returns true if removed
    @Transactional
    public boolean delete(Long id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return true;
        }
        return false;
    }
} 