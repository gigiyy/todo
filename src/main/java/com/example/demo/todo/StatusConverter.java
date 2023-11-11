package com.example.demo.todo;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, String> {

	@Override
	public String convertToDatabaseColumn(Status attribute) {
		if (attribute == null) {
			return null;
		}
		return attribute.getCode();
	}

	@Override
	public Status convertToEntityAttribute(String code) {
		if (code == null) {
			return null;
		}
		return Stream.of(Status.values())
			.filter(c -> c.getCode().equals(code))
			.findFirst()
			.orElseThrow(IllegalArgumentException::new);
	}

}
