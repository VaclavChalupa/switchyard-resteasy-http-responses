package org.jboss.soa.qa.resteasy.test;

import java.util.Arrays;
import java.util.stream.Stream;

public class TodoResourceTest extends RestInterfaceTest {

	private static final String TODO_PATH = "todos";

	@Override
	public String[] paths(String... paths) {
		return Stream.concat(Arrays.stream(new String[] {TODO_PATH}), Arrays.stream(paths)).toArray(String[]::new);
	}
}
