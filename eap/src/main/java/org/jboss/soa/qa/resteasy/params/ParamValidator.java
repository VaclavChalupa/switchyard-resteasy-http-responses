package org.jboss.soa.qa.resteasy.params;

import org.jboss.soa.qa.resteasy.errors.dto.ValidationErrorDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class ParamValidator {
	public static final Validator VALIDATOR;

	static {
		VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
	}

	public static <T> void validate(T object, Class<?>... groups) {
		final Set<ConstraintViolation<T>> cvs = VALIDATOR.validate(object, groups);

		if (cvs.size() > 0) {
			final List<ValidationErrorDto.Violation> vvs = new ArrayList<>(cvs.size());
			for (ConstraintViolation cv : cvs) {
				vvs.add(
						new ValidationErrorDto.Violation(
								parseField(cv.getPropertyPath().toString()),
								cv.getMessage()
						)
				);
			}

			// ExceptionMapper can not be registered, WAP must be thrown
			final Response response = Response
					.status(Response.Status.BAD_REQUEST)
					.entity(new ValidationErrorDto(vvs))
					.type(MediaType.APPLICATION_JSON_TYPE)
					.build();

			// Hack for RestEasy StringParameterInjector - only WAP is processed by exception mapper
			throw new WebApplicationException(response);
		}
	}

	private static String parseField(String path) {
		final int i = path.lastIndexOf('.');
		if (i > 0) {
			return path.substring(i + 1, path.length());
		}
		return path;
	}

	private ParamValidator() {
	}
}
