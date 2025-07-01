package com.example.todomysql.controller;

import com.example.todomysql.entity.Todo;
import com.example.todomysql.entity.TodoStatus;
import com.example.todomysql.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// REST controller for Todo CRUD endpoints (base path /api/v1/todos)
@RestController
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
@Tag(name = "Todo API", description = "CRUD operations for Todo entities")
public class TodoController {

    private final TodoService todoService;

    // List all to-do items with pagination and sorting
    @GetMapping
    @Operation(
        summary = "Get all todos with pagination, sorting and filtering",
        description = "Returns a paginated list of todos with optional sorting and status filtering"
    )
    public ResponseEntity<Map<String, Object>> getAllTodos(
            @Parameter(description = "Page number (zero-based)")
            @RequestParam(defaultValue = "0") int page,
            
            @Parameter(description = "Page size")
            @RequestParam(defaultValue = "10") int size,
            
            @Parameter(description = "Sort field")
            @RequestParam(defaultValue = "id") String sortBy,
            
            @Parameter(description = "Sort direction (asc or desc)")
            @RequestParam(defaultValue = "asc") String direction,
            
            @Parameter(description = "Filter by status (optional)")
            @RequestParam(required = false) TodoStatus status
    ) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? 
                Sort.Direction.DESC : Sort.Direction.ASC;
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        Page<Todo> todosPage;
        
        if (status != null) {
            todosPage = todoService.findByStatus(status, pageable);
        } else {
            todosPage = todoService.findAll(pageable);
        }

        Map<String, Object> body = new HashMap<>();
        body.put("message", "Todos fetched successfully");
        body.put("data", todosPage.getContent());
        body.put("currentPage", todosPage.getNumber());
        body.put("totalItems", todosPage.getTotalElements());
        body.put("totalPages", todosPage.getTotalPages());
        
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
    
    // Search todos by title
    @GetMapping("/search")
    @Operation(
        summary = "Search todos by title",
        description = "Returns a paginated list of todos whose titles contain the search term"
    )
    public ResponseEntity<Map<String, Object>> searchTodos(
            @Parameter(description = "Search term for title")
            @RequestParam(required = true) String title,
            
            @Parameter(description = "Page number (zero-based)")
            @RequestParam(defaultValue = "0") int page,
            
            @Parameter(description = "Page size")
            @RequestParam(defaultValue = "10") int size,
            
            @Parameter(description = "Sort field")
            @RequestParam(defaultValue = "id") String sortBy,
            
            @Parameter(description = "Sort direction (asc or desc)")
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? 
                Sort.Direction.DESC : Sort.Direction.ASC;
                
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        Page<Todo> todosPage = todoService.searchByTitle(title, pageable);

        Map<String, Object> body = new HashMap<>();
        body.put("message", "Todos searched successfully");
        body.put("data", todosPage.getContent());
        body.put("currentPage", todosPage.getNumber());
        body.put("totalItems", todosPage.getTotalElements());
        body.put("totalPages", todosPage.getTotalPages());
        
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    // Create a new to-do item
    @PostMapping
    @Operation(
        summary = "Create a new todo",
        description = "Creates a new todo item"
    )
    @ApiResponse(responseCode = "201", description = "Todo created successfully")
    public ResponseEntity<Map<String, Object>> createTodo(@RequestBody Todo todo) {
        Todo newTodo = todoService.create(todo);
        Map<String, Object> body = new HashMap<>();
        body.put("message", "Todo created successfully");
        body.put("data", newTodo);
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    // Get a to-do by id
    @GetMapping("/{id}")
    @Operation(
        summary = "Get a todo by ID",
        description = "Returns a single todo identified by its ID"
    )
    @ApiResponse(responseCode = "200", description = "Todo retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Todo not found")
    public ResponseEntity<?> getTodoById(
            @Parameter(description = "Todo ID", required = true)
            @PathVariable("id") long id
    ) {
        Todo todo = todoService.get(id);
        if (todo != null) {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Todo retrieved successfully");
            body.put("data", todo);
            return new ResponseEntity<>(body, HttpStatus.OK);
        } else {
            Map<String, String> body = new HashMap<>();
            body.put("message", "Todo not found");
            return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
        }
    }

    // Update a to-do by id
    @PutMapping("/{id}")
    @Operation(
        summary = "Update a todo",
        description = "Updates an existing todo identified by its ID"
    )
    @ApiResponse(responseCode = "200", description = "Todo updated successfully")
    @ApiResponse(responseCode = "404", description = "Todo not found")
    public ResponseEntity<?> updateTodo(
            @Parameter(description = "Todo ID", required = true)
            @PathVariable("id") long id, 
            @RequestBody Todo todo
    ) {
        Todo updated = todoService.update(id, todo);
        if (updated != null) {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Todo updated successfully");
            body.put("data", updated);
            return new ResponseEntity<>(body, HttpStatus.OK);
        } else {
            Map<String, String> body = new HashMap<>();
            body.put("message", "Todo not found");
            return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
        }
    }

    // Delete a to-do by id
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete a todo",
        description = "Deletes a todo identified by its ID"
    )
    @ApiResponse(responseCode = "200", description = "Todo deleted successfully")
    @ApiResponse(responseCode = "404", description = "Todo not found")
    public ResponseEntity<?> deleteTodo(
            @Parameter(description = "Todo ID", required = true)
            @PathVariable("id") long id
    ) {
        boolean deleted = todoService.delete(id);
        if (deleted) {
            Map<String, String> body = new HashMap<>();
            body.put("message", "Todo deleted successfully");
            return new ResponseEntity<>(body, HttpStatus.OK);
        } else {
            Map<String, String> body = new HashMap<>();
            body.put("message", "Todo not found");
            return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
        }
    }
} 