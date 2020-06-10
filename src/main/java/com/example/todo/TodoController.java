package com.example.todo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/todos/{id}")
    public Todo getTodo(@PathVariable Long id) {
        return todoService.findTodo(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found"));
    }

    @PostMapping("/todos")
    @ResponseStatus(HttpStatus.CREATED)
    public void postTodo(@RequestParam String title) {
        todoService.updateTodo(new Todo(null, title));
    }

    @PutMapping("/todos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putTodo(@PathVariable Long id, @RequestParam String title) {
        todoService.updateTodo(new Todo(id, title));
    }
}
