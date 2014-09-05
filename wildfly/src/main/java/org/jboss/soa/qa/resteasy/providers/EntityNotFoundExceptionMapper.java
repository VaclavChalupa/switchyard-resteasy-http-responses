package org.jboss.soa.qa.resteasy.providers;

import org.jboss.soa.qa.resteasy.exceptions.EntityNotFoundException;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

public class EntityNotFoundExceptionMapper implements SwitchyardExceptionMapper<EntityNotFoundException> {

	@Override
	public Response toResponse(EntityNotFoundException ex, HttpHeaders headers) {
		return Response
				.status(Response.Status.NOT_FOUND)
				.build();
	}
}
