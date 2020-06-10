package com.example.todo;

import java.util.List;
import java.util.Optional;

public interface TodoService {
    List<Todo> getAllTodos();
    void updateTodo(Todo todo);
    Optional<Todo> findTodo(Long id);
}
