package com.example.todomysql;

import org.springframework.web.bind.annotation.*;

@RestController
public class TodoMysqlController {
    @GetMapping("/todos")
    // connect to db
    // pull all the todos
    // return them

    @PostMapping("/todos")
    // extract data from the body
    // input validate
    // write to db
    // return success msg

    @GetMapping("/todos/{id}")
    // extract id from the url
    // check with db
    // if exists - return the todo
    // else return not found

    @PutMapping("/todos/{id}")
    // extract the id and data from body
    // check with db
    // if exists - write the new data and return success
    // else return not found

    @DeleteMapping("/todos/{id}")
    // extract id
    // check with db
    // if exists - delete and return success
    // else return not found
}


