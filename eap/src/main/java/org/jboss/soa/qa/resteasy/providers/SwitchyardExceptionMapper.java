package org.jboss.soa.qa.resteasy.providers;

import javax.ws.rs.core.Response;

public interface SwitchyardExceptionMapper<T extends Throwable> {

	Response toResponse(T ex);
}
