package org.jboss.soa.qa.resteasy.test;

import org.jboss.soa.qa.resteasy.errors.dto.ErrorDto;
import org.jboss.soa.qa.resteasy.errors.dto.ValidationErrorDto;
import org.jboss.soa.qa.resteasy.model.Todo;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class TodoResourceUpdateTest extends TodoResourceTest {

	private Todo created;

	@BeforeTest
	public void setUp() {
		final Todo newTodo = new Todo(null, "Test TodoResource#Update", false);
		created = authorizedRequest(paths()).accept(MediaType.APPLICATION_JSON).post(Entity.json(newTodo), Todo.class);
	}

	@Test
	public void updateShouldValidateTodo() {
		// TodoEntity may not have empty ID
		final Todo invalid = new Todo(null, null, false);
		final Response response = authorizedRequest(paths(created.getId().toString())).put(Entity.json(invalid));
		Assert.assertEquals(response.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());

		final ValidationErrorDto ve = response.readEntity(ValidationErrorDto.class);
		Assert.assertEquals(1, ve.getInvalidFields().size());
		Assert.assertEquals(ve.getInvalidFields().get(0).getField(), "id");
		Assert.assertEquals(ve.getInvalidFields().get(0).getMessage(), "may not be null");
	}

	@Test
	public void updateShouldValidateRequestConsistency() {
		final Response response = authorizedRequest(paths("" + Integer.MAX_VALUE)).put(Entity.json(created));
		Assert.assertEquals(response.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());

		final ErrorDto error = response.readEntity(ErrorDto.class);
		Assert.assertEquals(error.getCode(), "400 - INCONSISTENT REQUEST");
		Assert.assertEquals(error.getMessage(), "Entity id does not equals the requested path.");
	}

	@Test
	public void updateShouldReturnNotFoundForNonExistingTodo() {
		final Todo todo = new Todo(Long.MAX_VALUE, null, false);
		final Response response = authorizedRequest(paths("" + Long.MAX_VALUE)).put(Entity.json(todo));
		Assert.assertEquals(response.getStatus(), Response.Status.NOT_FOUND.getStatusCode());
	}

	@Test
	public void updateShouldUpdateTodo() {
		created.setText("New Text");
		created.setCompleted(true);

		final Response response = authorizedRequest(paths(created.getId().toString())).put(Entity.json(created));
		Assert.assertEquals(response.getStatus(), Response.Status.NO_CONTENT.getStatusCode());

		final Todo updated = authorizedRequest(paths(created.getId().toString()))
				.accept(MediaType.APPLICATION_JSON).get(Todo.class);
		Assert.assertEquals(updated.getId(), created.getId());
		Assert.assertEquals(updated.getText(), created.getText());
		Assert.assertEquals(updated.isCompleted(), created.isCompleted());
	}
}
