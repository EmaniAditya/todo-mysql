package com.example.todomysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class TodoMysqlController {
    
    @Autowired
    private TodoRepository todoRepository;

    @GetMapping("/todos")
    public ResponseEntity<Map<String, Object>> getAllTodos() {
        List<Todo> todos = todoRepository.findAll();
        Map<String, Object> body = new HashMap<>();
        body.put("message", "Todos fetched successfully");
        body.put("data", todos);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PostMapping("/todos")
    public ResponseEntity<Map<String, Object>> createTodo(@RequestBody Todo todo) {
        try {

            if (todo.getStatus() == null) {
                todo.setStatus(TodoStatus.TODO);
            }

            todo.setCreatedAt(LocalDateTime.now());

            Todo newTodo = todoRepository.save(todo);
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Todo created successfully");
            body.put("data", newTodo);
            return new ResponseEntity<>(body, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Failed to create todo");
            return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<?> getTodoById(@PathVariable("id") long id) {
        Optional<Todo> todoData = todoRepository.findById(id);
        if (todoData.isPresent()) {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Todo retrieved successfully");
            body.put("data", todoData.get());
            return new ResponseEntity<>(body, HttpStatus.OK);
        } else {
            Map<String, String> body = new HashMap<>();
            body.put("message", "Todo not found");
            return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable("id") long id, @RequestBody Todo todo) {
        Optional<Todo> todoData = todoRepository.findById(id);

        if (todoData.isPresent()) {
            Todo existingTodo = todoData.get();
            if (todo.getTitle() != null) {
                existingTodo.setTitle(todo.getTitle());
            }
            if (todo.getDescription() != null) {
                existingTodo.setDescription(todo.getDescription());
            }
            if (todo.getStatus() != null) {
                existingTodo.setStatus(todo.getStatus());
            }
            Todo savedTodo = todoRepository.save(existingTodo);
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Todo updated successfully");
            body.put("data", savedTodo);
            return new ResponseEntity<>(body, HttpStatus.OK);
        } else {
            Map<String, String> body = new HashMap<>();
            body.put("message", "Todo not found");
            return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") long id) {
        try {
            if (todoRepository.existsById(id)) {
                todoRepository.deleteById(id);
                Map<String, String> body = new HashMap<>();
                body.put("message", "Todo deleted successfully");
                return new ResponseEntity<>(body, HttpStatus.OK);
            } else {
                Map<String, String> body = new HashMap<>();
                body.put("message", "Todo not found");
                return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            Map<String, String> body = new HashMap<>();
            body.put("message", "Failed to delete todo");
            return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
