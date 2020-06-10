package com.example.todo;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public class SpyTodoService implements TodoService {

    private boolean allTodosIscalled;
    private List<Todo> todos;
    private Todo updateTodo_paramValue;
    private Long findTodo_paramValue;
    private Optional<Todo> findTodo_returnTodo = Optional.empty();

    public boolean getAllTodosIscalled() {
        return allTodosIscalled;
    }

    @Override
    public List<Todo> getAllTodos() {
        allTodosIscalled = true;
        return todos;
    }

    @Override
    public void updateTodo(Todo todo) {
        updateTodo_paramValue = todo;
    }

    @Override
    public Optional<Todo> findTodo(Long id) {
        findTodo_paramValue = id;
        return findTodo_returnTodo;
    }


    public void setGetAllTodos_returnAllTodos(List<Todo> todos) {
        this.todos = todos;
    }

    public Todo getUpdateTodo_paramValue() {
        return updateTodo_paramValue;
    }

    public Long getFindTodo_paramValue() {
        return findTodo_paramValue;
    }

    public void setFindTodo_returnTodo(Todo findTodo_returnTodo) {
        this.findTodo_returnTodo = Optional.of(findTodo_returnTodo);
    }
}
