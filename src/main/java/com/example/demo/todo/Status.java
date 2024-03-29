package com.example.demo.todo;

import lombok.Getter;

@Getter
public enum Status {
	Active("A"), Completed("C"), Deleted("D");
	private final String code;

	Status(String code) {
		this.code = code;
	}

}
