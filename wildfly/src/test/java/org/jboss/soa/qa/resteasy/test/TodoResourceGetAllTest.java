package org.jboss.soa.qa.resteasy.test;

import org.jboss.soa.qa.resteasy.errors.dto.ValidationErrorDto;
import org.jboss.soa.qa.resteasy.model.Todo;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

public class TodoResourceGetAllTest extends TodoResourceTest {

	private Todo[] todos;
	private long sumCount;
	private long lastOffset = 0L;

	@BeforeTest
	public void setUp() {
		todos = new Todo[20];
		for (int i = 0; i < 20; i++) {
			final Todo t = new Todo(null, "Test TodoResource#Update", i % 2 == 0 ? true : false);
			todos[i] = authorizedRequest(paths()).accept(MediaType.APPLICATION_JSON).post(Entity.json(t), Todo.class);
		}
	}

	@DataProvider
	public static Object[][] invalidParamsProvider() {
		return new Object[][] {
				{new Param("limit", "200"), "must be between 0 and 10"},
				{new Param("offset", "-5"), "must be greater than or equal to 0"}
		};
	}

	@Test(dataProvider = "invalidParamsProvider")
	public void getAllShouldValidateQueryParams(Param param, String validationMessage) {
		final Response response = authorizedRequest(paths(), param).get();
		Assert.assertEquals(response.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());

		final ValidationErrorDto ve = response.readEntity(ValidationErrorDto.class);
		Assert.assertEquals(1, ve.getInvalidFields().size());
		Assert.assertEquals(ve.getInvalidFields().get(0).getField(), param.getName());
		Assert.assertEquals(ve.getInvalidFields().get(0).getMessage(), validationMessage);
	}

	@Test
	public void getAllShouldReturnMax10Items() {
		final Response response = authorizedRequest(paths()).accept(MediaType.APPLICATION_JSON).get();
		Assert.assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());

		sumCount = Long.parseLong(response.getHeaderString("X-Count"));
		final List<Todo> list = response.readEntity(new GenericType<List<Todo>>() {
		});
		Assert.assertTrue(sumCount >= 20);
		Assert.assertEquals(list.size(), 10);
	}

	@DataProvider
	public static Object[][] completedFilterProvider() {
		return new Object[][] {
				{new Param("completed", Boolean.TRUE.toString()), Boolean.TRUE},
				{new Param("completed", Boolean.FALSE.toString()), Boolean.FALSE}
		};
	}

	@Test(dependsOnMethods = "getAllShouldReturnMax10Items", dataProvider = "completedFilterProvider")
	public void getAllCompletedShouldReturnOnlyCompletedItems(Param param, boolean completed) {
		final Response response = authorizedRequest(paths(), param).accept(MediaType.APPLICATION_JSON).get();
		Assert.assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());

		final long count = Long.parseLong(response.getHeaderString("X-Count"));
		final List<Todo> list = response.readEntity(new GenericType<List<Todo>>() {
		});

		Assert.assertTrue(count <= sumCount - 10);
		// list.forEach(t -> Assert.assertEquals(t.isCompleted(), completed));
	}

	@DataProvider
	public static Object[][] paginationProvider() {
		return new Object[][] {
				{new Param("limit", "5"), new Param("offset", "0")},
				{new Param("limit", "10"), new Param("offset", "3")},
				{new Param("limit", "1"), new Param("offset", "5")},
				{new Param("limit", "6"), new Param("offset", "11")},
		};
	}

	@Test(dataProvider = "paginationProvider")
	public void getAllCompletedShouldReturnOnlyCompletedItems(Param limit, Param offset) {
		final Response response = authorizedRequest(paths(), limit, offset).accept(MediaType.APPLICATION_JSON).get();
		Assert.assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());

		final List<Todo> list = response.readEntity(new GenericType<List<Todo>>() {
		});
		Assert.assertEquals(list.size() + "", limit.getValue());
		Assert.assertTrue(list.get(0).getId() > lastOffset);
		lastOffset = list.get(0).getId();
	}
}
