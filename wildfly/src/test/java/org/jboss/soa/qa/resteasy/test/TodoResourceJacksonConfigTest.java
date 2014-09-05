package org.jboss.soa.qa.resteasy.test;

import org.jboss.soa.qa.resteasy.model.Todo;

import org.testng.Assert;
import org.testng.annotations.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

public class TodoResourceJacksonConfigTest extends TodoResourceTest {

	@Test
	public void nullParamsShouldNotBeReturnedInJson() {
		Todo todo = new Todo(null, null, false);
		todo = authorizedRequest(paths()).accept(MediaType.APPLICATION_JSON).post(Entity.json(todo), Todo.class);

		final String response = authorizedRequest(paths(todo.getId().toString()))
				.accept(MediaType.APPLICATION_JSON).get(String.class);
		Assert.assertFalse(response.contains("text"));
	}
}
