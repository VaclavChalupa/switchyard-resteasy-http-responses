package org.jboss.soa.qa.resteasy;

import org.jboss.soa.qa.resteasy.db.TodoStorage;
import org.jboss.soa.qa.resteasy.exceptions.EntityNotFoundException;
import org.jboss.soa.qa.resteasy.exceptions.InconsistentRequestException;
import org.jboss.soa.qa.resteasy.model.Todo;

import org.switchyard.Context;
import org.switchyard.Property;
import org.switchyard.component.bean.Reference;
import org.switchyard.component.bean.Service;

import javax.inject.Inject;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(TodoService.class)
public class TodoServiceBean implements TodoService {

	@Inject
	private Context context;

	@Inject
	private TodoStorage todos;

	@Inject @Reference
	private AuthorizedService authorizedService;

	@Override
	public List<Todo> getAll() {
		final Boolean completed = (Boolean) getPropertyValue(Register.TodoResource.GetAll.COMPLETED);
		final int limit = (Integer) getPropertyValue(Register.PaginatedResource.LIMIT);
		final int offset = (Integer) getPropertyValue(Register.PaginatedResource.OFFSET);

		List<Todo> list;

		if (completed != null) {
			if (completed) {
				list = todos.getCompleted(limit, offset);
			} else {
				list = todos.getUncompleted(limit, offset);
			}
		} else {
			list = todos.getAll(limit, offset);
		}
		return list;
	}

	@Override
	public Todo get(Long id) throws EntityNotFoundException {
		return todos.get(id);
	}

	@Override
	public Todo create(Todo todo) {
		// Example of authorization required handler exception from backend service
		authorizedService.invoke();

		todos.create(todo);
		return todo;
	}

	@Override
	public Todo update(Todo todo) throws EntityNotFoundException {
		final long id = (Long) getPropertyValue(Register.TodoResource.Update.ID);

		// Example of authorization required handler exception from backend service
		authorizedService.invoke();

		if (id != todo.getId()) {
			throw new InconsistentRequestException();
		}
		return todos.update(todo.getId(), todo.getText(), todo.isCompleted());
	}

	@Override
	public Boolean delete(Long id) {
		// Example of authorization required handler exception from backend service
		authorizedService.invoke();

		try {
			todos.remove(id);
		} catch (EntityNotFoundException e) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	private Object getPropertyValue(String name) {
		for (Property p : context.getProperties()) {
			if (name.equals(p.getName())) {
				return p.getValue();
			}
		}
		return null;
	}
}
