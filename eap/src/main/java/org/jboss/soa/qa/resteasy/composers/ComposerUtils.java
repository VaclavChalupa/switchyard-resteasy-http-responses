package org.jboss.soa.qa.resteasy.composers;

import org.switchyard.Exchange;
import org.switchyard.HandlerException;

import java.lang.reflect.UndeclaredThrowableException;

public final class ComposerUtils {

	private static final String AUTHENTICATION_ERR = "Required policies have not been provided: clientAuthentication";
	private static final String AUTHORIZATION_ERR = "Required policies have not been provided: authorization";

	public static HandlerException getAuthenticationException(Exchange exchange) {
		return getAuthenticationException(exchange.getMessage().getContent());
	}

	public static HandlerException getAuthenticationException(Object content) {
		if (content instanceof HandlerException && content.toString().contains(AUTHENTICATION_ERR)) {
			return (HandlerException) content;
		}
		return null;
	}

	public static HandlerException getAuthorizationException(Exchange exchange) {
		return getAuthorizationException(exchange.getMessage().getContent());
	}

	public static HandlerException getAuthorizationException(Object content) {
		if (content instanceof HandlerException) {
			final HandlerException handlerException = (HandlerException) content;
			// if authorization is required for front services
			if (handlerException.toString().contains(AUTHORIZATION_ERR)) {
				return handlerException;
			}

			// if authorization is required for following backend (external) services
			Throwable throwable = handlerException.getCause();
			while (throwable != null) {
				Throwable next;
				if (throwable instanceof UndeclaredThrowableException
						&& ((UndeclaredThrowableException) throwable).getUndeclaredThrowable() != null) {
					next = ((UndeclaredThrowableException) throwable).getUndeclaredThrowable();
				} else if (throwable.getCause() != null) {
					next = throwable.getCause();
				} else {
					break;
				}

				if (next instanceof HandlerException && next.toString().contains(AUTHORIZATION_ERR)) {
					return (HandlerException) next;
				}
				throwable = next;
			}
		}
		return null;
	}

	private ComposerUtils() {
	}
}
