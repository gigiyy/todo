package com.example.todo;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

class TodoServiceTest {
    TodoService todoService;
    SpyTodoRepository spyTodoRepository;

    @BeforeEach
    void setUp() {

        spyTodoRepository = new SpyTodoRepository();
        todoService = new TodoServiceImpl(spyTodoRepository);
    }

    @Test
    public void getAllTodos_then_callsFindAll() {
        spyTodoRepository.setFindAll_returnValue(List.of(new Todo(1L, "dummy title")));
        List<Todo> returned = todoService.getAllTodos();
        assertThat(returned, equalTo(List.of(new Todo(1L, "dummy title"))));

        assertThat(spyTodoRepository.getFindAllIsCalled(), is(true));
    }
}