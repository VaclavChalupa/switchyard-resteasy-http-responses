package org.jboss.soa.qa.resteasy.providers;

import org.jboss.soa.qa.resteasy.exceptions.EntityNotFoundException;
import org.jboss.soa.qa.resteasy.exceptions.InconsistentRequestException;

import javax.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;

public final class SwitchYardExceptionMapperProvider {

	private static final Map<Class<? extends Exception>, SwitchyardExceptionMapper> mappers = new HashMap<>(4);

	static {
		mappers.put(InconsistentRequestException.class, new InconsistentRequestExceptionMapper());
		mappers.put(EntityNotFoundException.class, new EntityNotFoundExceptionMapper());
	}

	public static <T extends Throwable> Response handle(T e) {
		final SwitchyardExceptionMapper mapper = mappers.get(e.getClass());
		if (mapper == null) {
			return null;
		}
		return mapper.toResponse(e);
	}

	private SwitchYardExceptionMapperProvider() {
	}
}
