package org.jboss.soa.qa.resteasy.test;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import org.testng.annotations.AfterMethod;

import javax.ws.rs.client.Invocation;
import javax.xml.bind.DatatypeConverter;

import java.io.UnsupportedEncodingException;

import lombok.AllArgsConstructor;
import lombok.Getter;

public abstract class RestInterfaceTest {

	private static final String API_URL = "http://localhost:8080/api/v1";

	private static final String AUTHENTICATED_U = "authenticated";
	private static final String AUTHENTICATED_P = "authenticated.p1";
	private static final String AUTHORIZED_U = "authorized";
	private static final String AUTHORIZED_P = "authorized.p1";

	@AllArgsConstructor
	public static class Param {

		@Getter
		private String name;
		@Getter private String value;
	}

	private ResteasyClient client;

	@AfterMethod
	public void prepareTest() {
		if (client != null) {
			client.close();
			client = null;
		}
	}

	@AfterMethod
	public void cleanupTest() {
		if (client != null) {
			client.close();
			client = null;
		}
	}

	protected Invocation.Builder authenticatedRequest(String[] paths) {
		return authenticatedRequest(paths, new Param[] {});
	}

	protected Invocation.Builder authenticatedRequest(String[] paths, Param... params) {
		return addAuthorizationHeader(request(paths, params), AUTHENTICATED_U, AUTHENTICATED_P);
	}

	protected Invocation.Builder authorizedRequest(String[] paths) {
		return authorizedRequest(paths, new Param[] {});
	}

	protected Invocation.Builder authorizedRequest(String[] paths, Param... params) {
		return addAuthorizationHeader(request(paths, params), AUTHORIZED_U, AUTHORIZED_P);
	}

	protected Invocation.Builder request(String[] paths) {
		return request(paths, new Param[] {});
	}

	protected Invocation.Builder request(String[] paths, Param... params) {
		ResteasyWebTarget target = getClient().target(API_URL);
		for (String p : paths) {
			target = target.path(p);
		}
		for (Param p : params) {
			target = target.queryParam(p.getName(), p.getValue());
		}
		return target.request();
	}

	protected abstract String[] paths(String... paths);

	private ResteasyClient getClient() {
		if (client == null) {
			client = new ResteasyClientBuilder().build();
		}
		return client;
	}

	private Invocation.Builder addAuthorizationHeader(Invocation.Builder request, String user, String password) {
		return request.header("Authorization", createBasicAuth(user, password));
	}

	private String createBasicAuth(String user, String password) {
		final String token = user + ":" + password;
		try {
			return "Basic " + DatatypeConverter.printBase64Binary(token.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException ex) {
			throw new IllegalStateException("Cannot encode with UTF-8", ex);
		}
	}
}
