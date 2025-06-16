package com.example.todomysql;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoMysqlApplication {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllTodos() throws Exception {
        mockMvc.perform(get("/todos"))
               .andExpect(status().isOk());
    }

    @Test
    void testCreateTodo() throws Exception {
        mockMvc.perform(post("/todos"))
                .andExpect(status().isBadRequest()); // expecting 400, cause we are not sending an todo data in the body
    }

    @Test
    void testCreateTodoSuccess() throws Exception {
        mockMvc.perform(post("/todos")
                .contentType("application/json")
                .content("{\"title\":\"sample\",\"description\":\"desc\",\"status\":\"TODO\"}"))
               .andExpect(status().isCreated());
    }

    @Test
    void testGetATodo() throws Exception {
        mockMvc.perform(get("/todos/id"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateTodoNotFound() throws Exception {
        mockMvc.perform(put("/todos/999")
                .contentType("application/json")
                .content("{\"title\":\"new\"}"))
               .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateTodoBadRequest() throws Exception {
        mockMvc.perform(put("/todos/1"))
               .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteTodoNotFound() throws Exception {
        mockMvc.perform(delete("/todos/999"))
               .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteTodoBadRequest() throws Exception {
        mockMvc.perform(delete("/todos/null"))
               .andExpect(status().isBadRequest());
    }
}