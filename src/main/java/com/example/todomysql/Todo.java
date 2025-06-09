package com.example.todomysql;

import com.example.todomysql.model.TodoStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "todos")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
}
