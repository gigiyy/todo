package com.example.todo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

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

    @Test
    public void addTodo_then_save() {
        todoService.addTodo(new Todo(null, "good title"));
        Todo saved = spyTodoRepository.getSave_paramValue();
        assertThat(saved.getTitle(), is("good title"));
    }
}