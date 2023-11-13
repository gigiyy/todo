package com.example.demo.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ErrorTests {

	@LocalServerPort
	private int port;

	WebTestClient client;

	@BeforeEach
	public void setUp() {
		String url = "http://localhost:" + port;
		client = WebTestClient.bindToServer().baseUrl(url).build();
	}

	@Test
	public void should_returnNotFound() {
		// given

		// when
		client.get().uri("/notfound").exchange()
			.expectStatus()
			.isNotFound()
			.expectBody()
			.consumeWith(System.out::println)
			.jsonPath("$.path").isEqualTo("/notfound")
			.jsonPath("$.error").isEqualTo("Not Found")
			.jsonPath("$.status").isEqualTo("404");

		// then

	}

}
