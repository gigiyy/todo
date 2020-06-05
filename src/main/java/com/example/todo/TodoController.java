package com.example.todo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoController {

    private TodoService todoService;

    public TodoController(TodoService todoService) {

        this.todoService = todoService;
    }

    @GetMapping("/todos")
    public List<Todo> todoList() {
        return todoService.getAllTodos();
    }

    @PostMapping("/todos")
    @ResponseStatus(HttpStatus.CREATED)
    public void postTodo(@RequestParam String title) {
        todoService.addTodo(new Todo(null, title));
    }
}
