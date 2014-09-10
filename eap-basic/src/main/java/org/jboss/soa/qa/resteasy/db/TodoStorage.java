package org.jboss.soa.qa.resteasy.db;

import org.jboss.soa.qa.resteasy.exceptions.EntityNotFoundException;
import org.jboss.soa.qa.resteasy.model.Todo;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * External service mock.
 */
@Singleton
@Startup
public class TodoStorage {

	private Map<Long, Todo> todos = new TreeMap<>();
	private long nextId = 1L;

	private List<Todo> all() {
		return new ArrayList<>(todos.values());
	}

	private List<Todo> completed() {
		final ArrayList<Todo> completed = new ArrayList<>();
		for (Todo t: todos.values()) {
			if (t.isCompleted()) {
				completed.add(t);
			}
		}
		return completed;
	}

	private List<Todo> uncompleted() {
		final ArrayList<Todo> uncompleted = new ArrayList<>();
		for (Todo t: todos.values()) {
			if (!t.isCompleted()) {
				uncompleted.add(t);
			}
		}
		return uncompleted;
	}

	public List<Todo> getAll(int limit, int offset) {
		return sublist(all(), limit, offset);
	}

	public int getAllCount() {
		return all().size();
	}

	public List<Todo> getCompleted(int limit, int offset) {
		return sublist(completed(), limit, offset);
	}

	public int getCompletedCount() {
		return completed().size();
	}

	public List<Todo> getUncompleted(int limit, int offset) {
		return sublist(uncompleted(), limit, offset);
	}

	public int getUncompletedCount() {
		return uncompleted().size();
	}

	public Todo get(long id) throws EntityNotFoundException {
		final Todo todo = todos.get(id);
		if (todo == null) {
			throw new EntityNotFoundException();
		}
		return todo;
	}

	public void create(Todo todo) {
		todo.setId(nextId);
		todos.put(nextId++, todo);
	}

	public Todo update(long id, String text, boolean completed) throws EntityNotFoundException {
		final Todo todo = todos.get(id);
		if (todo == null) {
			throw new EntityNotFoundException();
		}
		todo.setText(text);
		todo.setCompleted(completed);
		return todo;
	}

	public void remove(long id) throws EntityNotFoundException {
		if (!todos.containsKey(id)) {
			throw new EntityNotFoundException();
		}
		todos.remove(id);
	}

	private List<Todo> sublist(List<Todo> list, int limit, int offset) {
		if (list.isEmpty()) {
			return list;
		}
		if (offset >= list.size()) {
			return Collections.emptyList();
		}
		return list.subList(offset, (offset + limit >= list.size() ? list.size() : offset + limit));
	}

	@PostConstruct
	private void setup() {
		Todo todo = new Todo();
		todo.setText("Do something");
		create(todo);

		todo = new Todo();
		todo.setText("Do something else");
		create(todo);
	}
}
