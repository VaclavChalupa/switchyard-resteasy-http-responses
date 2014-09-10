package org.jboss.soa.qa.resteasy.errors.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class ValidationErrorDto extends ErrorDto {

	@NoArgsConstructor
	@AllArgsConstructor
	public static class Violation {

		@Getter @Setter private String field;
		@Getter @Setter private String message;
	}

	public ValidationErrorDto(List<Violation> fields) {
		super("400 - VALIDATION", "Validation Error");
		this.invalidFields = fields;
	}

	@Getter @Setter private List<Violation> invalidFields;
}
