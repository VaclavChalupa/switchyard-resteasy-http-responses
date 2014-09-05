package org.jboss.soa.qa.resteasy.params;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.ws.rs.WebApplicationException;

import java.util.Set;

public final class ParamValidator {
	public static final Validator VALIDATOR;

	static {
		VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
	}

	public static <T> void validate(T object) {
		final Set<ConstraintViolation<T>> cvs = VALIDATOR.validate(object);
		if (cvs.size() > 0) {
			// Hack for RestEasy StringParameterInjector - only WAP is processed by exception mapper
			throw new WebApplicationException(new ConstraintViolationException(cvs));
		}
	}

	private ParamValidator() {
	}
}
