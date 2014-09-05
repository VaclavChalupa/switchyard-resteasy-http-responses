package org.jboss.soa.qa.resteasy.providers;

import org.jboss.soa.qa.resteasy.errors.dto.ValidationErrorDto;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

public final class ViolationExceptionMapperUtil {

	public static String parseField(String path) {
		final int i = path.lastIndexOf('.');
		if (i > 0) {
			return path.substring(i + 1, path.length());
		}
		return path;
	}

	public static Response badRequest(List<ValidationErrorDto.Violation> vvs, HttpHeaders headers) {
		return Response
				.status(Response.Status.BAD_REQUEST)
				.entity(new ValidationErrorDto(vvs))
				.type(headers.getMediaType() != null ? headers.getMediaType() : MediaType.APPLICATION_JSON_TYPE)
				.build();
	}

	private ViolationExceptionMapperUtil() {
	}
}
