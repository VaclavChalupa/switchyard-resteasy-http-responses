package org.jboss.soa.qa.resteasy.test;

public class TodoResourceTest extends RestInterfaceTest {

	private static final String TODO_PATH = "todos";

	@Override
	public String[] paths(String... paths) {
		final String[] completePaths = new String[paths.length + 1];
		completePaths[0] = TODO_PATH;
		System.arraycopy(paths, 0, completePaths, 1, paths.length);
		return completePaths;
	}
}
