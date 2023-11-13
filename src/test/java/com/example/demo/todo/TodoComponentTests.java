package com.example.demo.todo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import reactor.core.publisher.Mono;

@ActiveProfiles("container")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoComponentTests {

	@Container
	@ServiceConnection
	private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:11.1")
		.withDatabaseName("integration-tests-db")
		.withUsername("sa")
		.withPassword("sa");


	static {
		postgres.start();
	}

	WebTestClient client;

	@LocalServerPort
	private int port;

	@BeforeEach
	public void setUp() {
		String url = "http://localhost:" + port;
		client = WebTestClient.bindToServer().baseUrl(url).build();
	}

	@Test
	public void should_runComponentTests() {
		// given
		var todos = List.of(TodoItem.builder().title("title1").build(),
			TodoItem.builder().title("title2").build(),
			TodoItem.builder().title("title3").build());

		// when
		String todoUrl = "/todos";
		todos.forEach(todo -> client.post().uri(todoUrl)
			.accept(MediaType.APPLICATION_JSON)
			.body(Mono.just(todo), TodoItem.class)
			.exchange()
			.expectStatus().isCreated());

		client.get().uri(todoUrl).exchange().expectStatus().isOk()
			.expectBodyList(TodoItem.class).hasSize(3)
			.value(items -> assertThat(items).allMatch(item -> item.getStatus() == Status.Active));
		// then
		client.post().uri(todoUrl + "/1/complete")
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$.status").isEqualTo("Completed");

		String statusByValue = "$[?(@.status == '%s')]";
		client.get().uri(todoUrl).exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath(statusByValue, "Completed").exists()
			.jsonPath(statusByValue, "Active").exists();

	}
}
