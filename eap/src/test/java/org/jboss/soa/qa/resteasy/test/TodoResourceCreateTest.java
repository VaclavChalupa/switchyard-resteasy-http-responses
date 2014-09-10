package org.jboss.soa.qa.resteasy.test;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import org.jboss.soa.qa.resteasy.errors.dto.ValidationErrorDto;
import org.jboss.soa.qa.resteasy.model.Todo;

import org.testng.Assert;
import org.testng.annotations.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

public class TodoResourceCreateTest extends TodoResourceTest {

	@Test
	public void createShouldValidateTodo() {
		// TodoEntity must have empty ID
		final Todo invalid = new Todo(1L, null, false);
		final Response response = authorizedRequest(paths()).accept(APPLICATION_JSON).post(Entity.json(invalid));
		Assert.assertEquals(response.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());

		final ValidationErrorDto ve = response.readEntity(ValidationErrorDto.class);
		Assert.assertEquals(1, ve.getInvalidFields().size());
		Assert.assertEquals(ve.getInvalidFields().get(0).getField(), "id");
		Assert.assertEquals(ve.getInvalidFields().get(0).getMessage(), "must be null");
	}

	@Test
	public void createShouldPersistTodo() {
		final Todo newTodo = new Todo(null, "Test TodoResource#create", false);
		final Response response = authorizedRequest(paths()).accept(APPLICATION_JSON).post(Entity.json(newTodo));
		Assert.assertEquals(response.getStatus(), Response.Status.CREATED.getStatusCode());

		final Todo todo = response.readEntity(Todo.class);
		Assert.assertTrue(todo.getId() != null);
		Assert.assertEquals(todo.getText(), newTodo.getText());
		Assert.assertEquals(todo.isCompleted(), newTodo.isCompleted());
		Assert.assertEquals(response.getLocation().toString(), "/api/v1/todos/" + todo.getId());
	}
}
