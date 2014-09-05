package org.jboss.soa.qa.resteasy.test;

import org.jboss.soa.qa.resteasy.model.Todo;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class TodoResourceDeleteTest extends TodoResourceTest {

	private Todo created;

	@BeforeTest
	public void setUp() {
		final Todo newTodo = new Todo(null, "Test TodoResource#Delete", false);
		created = authorizedRequest(paths()).accept(MediaType.APPLICATION_JSON).post(Entity.json(newTodo), Todo.class);
	}

	@Test
	public void deleteShouldRemoveTodo() {
		// TodoEntity may not have empty ID
		Response response = authorizedRequest(paths(created.getId().toString())).delete();
		Assert.assertEquals(response.getStatus(), Response.Status.NO_CONTENT.getStatusCode());

		response = authorizedRequest(paths(created.getId().toString())).accept(MediaType.APPLICATION_JSON).get();
		Assert.assertEquals(response.getStatus(), Response.Status.NOT_FOUND.getStatusCode());
	}

	@Test
	public void deleteShouldBeIdempotent() {
		final Response response = authorizedRequest(paths("" + Integer.MAX_VALUE)).delete();
		Assert.assertEquals(response.getStatus(), Response.Status.NO_CONTENT.getStatusCode());
	}
}
