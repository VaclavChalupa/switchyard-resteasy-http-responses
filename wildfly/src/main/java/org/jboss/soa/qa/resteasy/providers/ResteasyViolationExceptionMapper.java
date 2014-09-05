package org.jboss.soa.qa.resteasy.providers;

import org.jboss.resteasy.api.validation.ResteasyViolationException;
import org.jboss.soa.qa.resteasy.errors.dto.ValidationErrorDto;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import java.util.ArrayList;
import java.util.List;

@Provider
public class ResteasyViolationExceptionMapper implements ExceptionMapper<ResteasyViolationException> {

	@Context
	private HttpHeaders headers;

	@Override
	public Response toResponse(ResteasyViolationException ex) {
		final List<ValidationErrorDto.Violation> vvs = new ArrayList<>();
		ex.getParameterViolations().stream().forEach(
				cv -> vvs.add(
						new ValidationErrorDto.Violation(
								ViolationExceptionMapperUtil.parseField(cv.getPath()),
								cv.getMessage()
						)
				)
		);

		return ViolationExceptionMapperUtil.badRequest(vvs, headers);
	}
}
