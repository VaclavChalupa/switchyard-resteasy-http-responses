package org.jboss.soa.qa.resteasy.params;

import org.jboss.soa.qa.resteasy.model.Todo;
import org.jboss.soa.qa.resteasy.validation.group.Create;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

public class CreateTodoParam {

	private Todo todo;

	@JsonCreator
	public CreateTodoParam(
			@JsonProperty("id") Long id,
			@JsonProperty("text") String text,
			@JsonProperty("completed") boolean completed) {

		todo = new Todo(id, text, completed);
	}

	public Todo getTodo() {
		// RestEasy can not handle WAE by param binding, validation must be lazy
		ParamValidator.validate(todo, Create.class); // validate TodoEntity
		return todo;
	}
}
