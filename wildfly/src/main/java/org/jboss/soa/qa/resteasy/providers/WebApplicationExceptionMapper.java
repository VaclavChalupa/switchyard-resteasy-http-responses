package org.jboss.soa.qa.resteasy.providers;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

	@Context
	private HttpHeaders headers;

	@Override
	public Response toResponse(WebApplicationException ex) {
		// Hack for SwitchYard - only WAE is passed to RestEasy exception handler
		// Hack for RestEasy StringParameterInjector - only WAP is processed by exception mapper
		Response r = null;
		if (ex.getCause() != null) {
			r = SwitchYardExceptionMapperProvider.handle(ex.getCause(), headers);
		}
		return r != null ? r : ex.getResponse();
	}
}
