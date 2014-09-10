package org.jboss.soa.qa.resteasy;

import org.jboss.soa.qa.resteasy.exceptions.EntityNotFoundException;
import org.jboss.soa.qa.resteasy.model.ListResource;
import org.jboss.soa.qa.resteasy.model.Todo;

public interface TodoService {

	ListResource<Todo> getAll();
	Todo get(Long id) throws EntityNotFoundException;
	Todo create(Todo todo);
	Todo update(Todo todo) throws EntityNotFoundException;
	Boolean delete(Long id);
}
