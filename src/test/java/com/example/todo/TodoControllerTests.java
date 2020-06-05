package com.example.todo;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

public class TodoControllerTests {
    SpyTodoService spyTodoService;

    @BeforeEach
    public void setup() {
        spyTodoService = new SpyTodoService();
        RestAssuredMockMvc.standaloneSetup(new TodoController(spyTodoService));
    }

    @Test
    public void getTodos_returns_status_OK() {
        spyTodoService.setGetAllTodos_returnAllTodos(Lists.emptyList());
        given()
                .when()
                .get("/todos")
                .then()
                .statusCode(OK.value())
                .contentType(JSON);
    }

    @Test
    public void geTodos_call_todoService_getAllTodos() {
        spyTodoService.setGetAllTodos_returnAllTodos(List.of(new Todo(1L, "dummy title")));
        given()
                .when()
                .get("/todos")
                .then()
                .body("[0].title", equalTo("dummy title"));

        assertThat(spyTodoService.getAllTodosIscalled(), is(true));

    }

    @Test
    public void postTodos_returns_status_created() {
        given()
                .param("title", "good title")
                .when()
                .post("/todos")
                .then()
                .statusCode(CREATED.value());

        Todo param = spyTodoService.getAddTodo_paramValue();
        assertThat(param.getTitle(), is("good title"));
    }
}
