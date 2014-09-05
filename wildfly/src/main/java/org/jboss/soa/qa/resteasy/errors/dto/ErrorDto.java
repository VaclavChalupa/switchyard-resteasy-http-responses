package org.jboss.soa.qa.resteasy.errors.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {

	@Getter @Setter private String code;
	@Getter @Setter private String message;
}
