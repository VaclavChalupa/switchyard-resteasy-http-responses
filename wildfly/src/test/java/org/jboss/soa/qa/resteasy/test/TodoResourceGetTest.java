package org.jboss.soa.qa.resteasy.test;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import org.jboss.soa.qa.resteasy.model.Todo;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

public class TodoResourceGetTest extends TodoResourceTest {

	private Todo created;

	@BeforeTest
	public void setUp() {
		final Todo newTodo = new Todo(null, "Test TodoResource#Get", false);
		created = authorizedRequest(paths()).accept(APPLICATION_JSON).post(Entity.json(newTodo), Todo.class);
	}

	@Test
	public void getShouldReturnExpectedTodo() {
		final Response response = authorizedRequest(paths(created.getId().toString())).accept(APPLICATION_JSON).get();
		Assert.assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());

		final Todo todo = response.readEntity(Todo.class);
		Assert.assertEquals(todo.getId(), created.getId());
		Assert.assertEquals(todo.getText(), created.getText());
		Assert.assertEquals(todo.isCompleted(), created.isCompleted());
	}

	@Test
	public void getShouldReturnNotFoundForNonExistingTodo() {
		final Response response = authorizedRequest(paths("" + Integer.MAX_VALUE)).accept(APPLICATION_JSON).get();
		Assert.assertEquals(response.getStatus(), Response.Status.NOT_FOUND.getStatusCode());
	}
}
