package org.jboss.soa.qa.resteasy.test;

import org.jboss.soa.qa.resteasy.model.Todo;

import org.testng.Assert;
import org.testng.annotations.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

public class TodoResourceAccessTest extends TodoResourceTest {

	@Test
	public void getAllShouldReturnUnauhthorized() {
		final Response response = request(paths()).get();
		Assert.assertEquals(response.getStatus(), Response.Status.UNAUTHORIZED.getStatusCode());
	}

	@Test
	public void getShouldReturnUnauhthorized() {
		final Response response = request(paths("1")).get();
		Assert.assertEquals(response.getStatus(), Response.Status.UNAUTHORIZED.getStatusCode());
	}

	@Test
	public void createShouldReturnUnauhthorized() {
		final Response response = request(paths()).post(Entity.json(new Todo()));
		Assert.assertEquals(response.getStatus(), Response.Status.UNAUTHORIZED.getStatusCode());
	}

	@Test
	public void updateShouldReturnUnauhthorized() {
		final Response response = request(paths("1")).put(Entity.json(new Todo(1L, null, false)));
		Assert.assertEquals(response.getStatus(), Response.Status.UNAUTHORIZED.getStatusCode());
	}

	@Test
	public void deleteShouldReturnUnauhthorized() {
		final Response response = request(paths("1")).delete();
		Assert.assertEquals(response.getStatus(), Response.Status.UNAUTHORIZED.getStatusCode());
	}

	@Test
	public void createShouldReturnForbidden() {
		final Response response = authenticatedRequest(paths()).post(Entity.json(new Todo()));
		Assert.assertEquals(response.getStatus(), Response.Status.FORBIDDEN.getStatusCode());
	}

	@Test
	public void updateShouldReturnForbidden() {
		final Response response = authenticatedRequest(paths("1")).put(Entity.json(new Todo(1L, null, false)));
		Assert.assertEquals(response.getStatus(), Response.Status.FORBIDDEN.getStatusCode());
	}

	@Test
	public void deleteShouldReturnForbidden() {
		final Response response = authenticatedRequest(paths("1")).delete();
		Assert.assertEquals(response.getStatus(), Response.Status.FORBIDDEN.getStatusCode());
	}
}
