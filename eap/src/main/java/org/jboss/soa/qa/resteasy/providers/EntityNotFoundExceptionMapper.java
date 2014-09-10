package org.jboss.soa.qa.resteasy.providers;

import org.jboss.soa.qa.resteasy.errors.dto.ErrorDto;
import org.jboss.soa.qa.resteasy.exceptions.EntityNotFoundException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class EntityNotFoundExceptionMapper implements SwitchyardExceptionMapper<EntityNotFoundException> {

	@Override
	public Response toResponse(EntityNotFoundException ex) {
		return Response
				.status(Response.Status.NOT_FOUND)
				.entity(new ErrorDto("404 - NOT FOUND", null))
				.type(MediaType.APPLICATION_JSON)
				.build();
	}
}
