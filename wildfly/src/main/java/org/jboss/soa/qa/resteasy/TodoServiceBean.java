package org.jboss.soa.qa.resteasy;

import org.jboss.soa.qa.resteasy.db.TodoStorage;
import org.jboss.soa.qa.resteasy.exceptions.EntityNotFoundException;
import org.jboss.soa.qa.resteasy.exceptions.InconsistentRequestException;
import org.jboss.soa.qa.resteasy.metadata.todo.GetAllMetadata;
import org.jboss.soa.qa.resteasy.metadata.todo.TodoResourceMetadataProducer;
import org.jboss.soa.qa.resteasy.metadata.todo.UpdateMetadata;
import org.jboss.soa.qa.resteasy.model.ListResource;
import org.jboss.soa.qa.resteasy.model.Todo;

import org.switchyard.component.bean.Reference;
import org.switchyard.component.bean.Service;

import javax.inject.Inject;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(TodoService.class)
public class TodoServiceBean implements TodoService {

	@Inject
	private TodoStorage todos;

	@Inject
	private TodoResourceMetadataProducer metadataProducer;

	@Inject @Reference
	private AuthorizedService authorizedService;

	@Override
	public ListResource<Todo> getAll() {
		final GetAllMetadata md = metadataProducer.getGetAllMetadata(); // user exchange context first
		final int limit = md.getLimit();
		final int offset = md.getOffset();
		List<Todo> list;
		int count;

		if (md.getCompleted() != null) {
			if (md.getCompleted()) {
				list = todos.getCompleted(limit, offset);
				count = todos.getCompletedCount();
			} else {
				list = todos.getUncompleted(limit, offset);
				count = todos.getUncompletedCount();
			}
		} else {
			list = todos.getAll(limit, offset);
			count = todos.getAllCount();
		}
		return new ListResource<>(list, count);
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
		final UpdateMetadata md = metadataProducer.getUpdateMetadata();

		// Example of authorization required handler exception from backend service
		authorizedService.invoke();

		if (md.getId() != todo.getId()) {
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
}
