package org.jboss.soa.qa.resteasy.providers;

import org.jboss.soa.qa.resteasy.errors.dto.ErrorDto;
import org.jboss.soa.qa.resteasy.exceptions.InconsistentRequestException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class InconsistentRequestExceptionMapper implements SwitchyardExceptionMapper<InconsistentRequestException> {

	@Override
	public Response toResponse(InconsistentRequestException ex) {
		return Response
				.status(Response.Status.BAD_REQUEST)
				.entity(new ErrorDto("400 - INCONSISTENT REQUEST", "Entity id does not equals the requested path."))
				.type(MediaType.APPLICATION_JSON_TYPE)
				.build();
	}
}
