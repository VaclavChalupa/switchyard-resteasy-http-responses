package org.jboss.soa.qa.resteasy.providers;

import org.jboss.soa.qa.resteasy.errors.dto.ValidationErrorDto;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import java.util.ArrayList;
import java.util.List;

@Provider
public class ConstraintViolationExceptionMapper implements SwitchyardExceptionMapper<ConstraintViolationException> {

	@Override
	public Response toResponse(ConstraintViolationException ex, HttpHeaders headers) {
		final List<ValidationErrorDto.Violation> vvs = new ArrayList<>();
		ex.getConstraintViolations().stream().forEach(
				cv -> vvs.add(
						new ValidationErrorDto.Violation(
								ViolationExceptionMapperUtil.parseField(cv.getPropertyPath().toString()),
								cv.getMessage()
						)
				)
		);
		return ViolationExceptionMapperUtil.badRequest(vvs, headers);
	}
}
