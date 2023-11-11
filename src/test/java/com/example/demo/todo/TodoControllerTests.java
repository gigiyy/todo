package com.example.demo.todo;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class TodoControllerTests {

	@InjectMocks
	TodoController controller;
	@Mock
	TodoRepository todoRepository;
	MockMvc mockMvc;
	private TodoItem item;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		item = TodoItem.builder().title("title").description("description").build();
	}

	@Test
	public void should_returnActiveTodoItemWithLocation_when_postedWithTitleAndDescription() throws Exception {
		// given
		TodoItem saving = item.toBuilder().status(Status.Active).build();
		when(todoRepository.save(saving)).thenReturn(saving.toBuilder().id(1L).build());

		// when
		mockMvc.perform(post("/todos")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(item)))
			.andExpectAll(
				status().isCreated(),
				header().string("Location", "/todos/1"),
				jsonPath("$.id").value(1),
				jsonPath("$.title").value("title"),
				jsonPath("$.description").value("description"),
				jsonPath("$.status").value("Active")
			);

		// then

	}

	@Test
	public void should_returnAllTodos() throws Exception {
		// given
		when(todoRepository.findAll()).thenReturn(
			List.of(TodoItem.builder().id(1L).title("title").description("description").build(),
				TodoItem.builder().id(2L).title("title2").description("description2").build()
			));
		// when
		mockMvc.perform(get("/todos"))
			.andExpectAll(
				status().isOk(),
				jsonPath("$[0].id").value(1),
				jsonPath("$[0].title").value("title"),
				jsonPath("$[0].description").value("description"),
				jsonPath("$[1].id").value(2),
				jsonPath("$[1].title").value("title2"),
				jsonPath("$[1].description").value("description2")
			);

		// then

	}

	@Test
	public void should_setTotoItemToCompleted_when_postedWithCompleteAction() throws Exception {
		// given
		TodoItem found = item.toBuilder().id(1L).build();
		TodoItem completed = found.toBuilder().status(Status.Completed).build();
		when(todoRepository.findById(1L)).thenReturn(Optional.of(found));
		when(todoRepository.save(completed)).thenReturn(completed);
		// when
		mockMvc.perform(post("/todos/1/complete"))
			.andExpectAll(
				status().isOk(),
				jsonPath("$.id").value(1),
				jsonPath("$.title").value("title"),
				jsonPath("$.description").value("description"),
				jsonPath("$.status").value("Completed")
			);

		// then

	}
}
