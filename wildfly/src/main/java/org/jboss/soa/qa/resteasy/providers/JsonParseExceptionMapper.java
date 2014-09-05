package org.jboss.soa.qa.resteasy.providers;

import org.jboss.soa.qa.resteasy.errors.dto.UnsupportedJsonFormatErrorDto;

import com.fasterxml.jackson.core.JsonParseException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class JsonParseExceptionMapper implements ExceptionMapper<JsonParseException> {

	@Context
	private HttpHeaders headers;

	@Override
	public Response toResponse(JsonParseException ex) {
		return Response
				.status(Response.Status.BAD_REQUEST)
				.entity(new UnsupportedJsonFormatErrorDto())
				.type(headers.getMediaType() != null ? headers.getMediaType() : MediaType.APPLICATION_JSON_TYPE)
				.build();
	}
}
