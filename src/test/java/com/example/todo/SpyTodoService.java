package com.example.todo;

import java.util.List;

public class SpyTodoService implements TodoService {

    private boolean allTodosIscalled;
    private List<Todo> todos;
    private Todo addTodo_paramValue;

    public boolean getAllTodosIscalled() {
        return allTodosIscalled;
    }

    @Override
    public List<Todo> getAllTodos() {
        allTodosIscalled = true;
        return todos;
    }

    @Override
    public void addTodo(Todo todo) {
        addTodo_paramValue = todo;
    }

    public void setGetAllTodos_returnAllTodos(List<Todo> todos) {
        this.todos = todos;
    }

    public Todo getAddTodo_paramValue() {
        return addTodo_paramValue;
    }
}
