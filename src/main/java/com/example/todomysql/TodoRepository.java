package com.example.todomysql.repository;

import com.example.todomysql.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    
}

