package com.example.todo;

import java.util.List;

public interface TodoService {
    List<Todo> getAllTodos();
    void addTodo(Todo todo);
}
