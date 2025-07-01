package com.example.todomysql.controller;

import com.example.todomysql.entity.Todo;
import com.example.todomysql.service.TodoService;
import lombok.RequiredArgsConstructor;
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
public class TodoController {

    private final TodoService todoService;

    // List all to-do items
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllTodos() {
        List<Todo> todos = todoService.findAll();
        Map<String, Object> body = new HashMap<>();
        body.put("message", "Todos fetched successfully");
        body.put("data", todos);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    // Create a new to-do item
    @PostMapping
    public ResponseEntity<Map<String, Object>> createTodo(@RequestBody Todo todo) {
        Todo newTodo = todoService.create(todo);
        Map<String, Object> body = new HashMap<>();
        body.put("message", "Todo created successfully");
        body.put("data", newTodo);
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    // Get a to-do by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getTodoById(@PathVariable("id") long id) {
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
    public ResponseEntity<?> updateTodo(@PathVariable("id") long id, @RequestBody Todo todo) {
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
    public ResponseEntity<?> deleteTodo(@PathVariable("id") long id) {
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