package com.example.demo.todo;

import static org.springframework.http.HttpStatus.CREATED;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

	final TodoRepository todoRepository;

	@PostMapping
	@ResponseStatus(code = CREATED)
	public TodoItem create(@RequestBody TodoItem item, HttpServletResponse response) {
		TodoItem saved = todoRepository.save(item.toBuilder().status(Status.Active).build());
		String location = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(saved.getId())
			.toUri().getPath();
		response.setHeader("Location", location);
		return saved;
	}

	@GetMapping
	public List<TodoItem> getAll() {
		return todoRepository.findAll();
	}

	@PostMapping("/{id}/complete")
	public TodoItem complete(@PathVariable Long id) {
		return todoRepository.findById(id)
			.map(item -> todoRepository.save(item.toBuilder().status(Status.Completed).build()))
			.orElseThrow(() -> new RuntimeException("Todo not found"));
	}
}
