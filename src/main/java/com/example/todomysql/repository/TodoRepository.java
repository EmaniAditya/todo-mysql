package com.example.todomysql.repository;

import com.example.todomysql.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Spring Data JPA repository for Todo entities
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

} 