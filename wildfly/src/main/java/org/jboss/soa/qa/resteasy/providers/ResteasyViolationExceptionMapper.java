package org.jboss.soa.qa.resteasy.providers;

import org.jboss.resteasy.api.validation.ResteasyViolationException;
import org.jboss.soa.qa.resteasy.TodoServiceResource;
import org.jboss.soa.qa.resteasy.errors.dto.ValidationErrorDto;
import org.jboss.soa.qa.resteasy.utils.StringUtils;
import org.jboss.soa.qa.resteasy.validation.ParamName;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
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
								getRealFieldName(parseField(cv.getPath())),
								cv.getMessage()
						)
				)
		);

		return Response
				.status(Response.Status.BAD_REQUEST)
				.entity(new ValidationErrorDto(vvs))
				.type(headers.getMediaType() != null ? headers.getMediaType() : MediaType.APPLICATION_JSON_TYPE)
				.build();
	}

	private String parseField(String path) {
		final int i = path.lastIndexOf('.');
		if (i > 0) {
			return path.substring(i + 1, path.length());
		}
		return path;
	}

	private String getRealFieldName(String field) {
		if (field.contains("arg")) {

			// Use some kind of register to search JAX-RS resource class and its method
			for (Method m : TodoServiceResource.class.getDeclaredMethods()) {
				if (m.getName().equals("getAll")) {
					final Parameter[] parameters = m.getParameters();
					final int arg = StringUtils.toInt(field.substring(3), -1);
					if (arg >= 0 && arg < parameters.length) {
						final ParamName pn = parameters[arg].getAnnotation(ParamName.class);
						if (pn != null) {
							return pn.value();
						}
					}
				}
			}
		}
		return field;
	}
}
