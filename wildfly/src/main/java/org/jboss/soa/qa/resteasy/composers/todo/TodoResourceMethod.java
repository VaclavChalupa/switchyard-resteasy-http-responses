package org.jboss.soa.qa.resteasy.composers.todo;

import org.jboss.soa.qa.resteasy.composers.ComposerFunction;
import org.jboss.soa.qa.resteasy.composers.DecomposerFunction;

import java.util.Arrays;

public enum TodoResourceMethod {

	GET_ALL("getAll") {

		@Override
		public ComposerFunction composer(TodoResourceComposer todoResourceComposer) {
			return todoResourceComposer::composeGetAll;
		}

		@Override
		public DecomposerFunction decomposer(TodoResourceComposer todoResourceComposer) {
			return todoResourceComposer::decomposeGetAll;
		}
	},
	GET("get") {

		@Override
		public ComposerFunction composer(TodoResourceComposer todoResourceComposer) {
			return todoResourceComposer::composeGet;
		}

		@Override
		public DecomposerFunction decomposer(TodoResourceComposer todoResourceComposer) {
			return todoResourceComposer::decomposeGet;
		}
	},
	CREATE("create") {

		@Override
		public ComposerFunction composer(TodoResourceComposer todoResourceComposer) {
			return todoResourceComposer::composeCreate;
		}

		@Override
		public DecomposerFunction decomposer(TodoResourceComposer todoResourceComposer) {
			return todoResourceComposer::decomposeCreate;
		}
	},
	UPDATE("update") {

		@Override
		public ComposerFunction composer(TodoResourceComposer todoResourceComposer) {
			return todoResourceComposer::composeUpdate;
		}

		@Override
		public DecomposerFunction decomposer(TodoResourceComposer todoResourceComposer) {
			return todoResourceComposer::decomposeUpdate;
		}
	},
	DELETE("delete") {

		@Override
		public ComposerFunction composer(TodoResourceComposer todoResourceComposer) {
			return todoResourceComposer::composeDelete;
		}

		@Override
		public DecomposerFunction decomposer(TodoResourceComposer todoResourceComposer) {
			return todoResourceComposer::decomposeDelete;
		}
	};

	private String methodName;

	TodoResourceMethod(String methodName) {
		this.methodName = methodName;
	}

	public String getMethodName() {
		return methodName;
	}

	public abstract ComposerFunction composer(TodoResourceComposer todoResourceComposer);
	public abstract DecomposerFunction decomposer(TodoResourceComposer todoResourceComposer);

	public static TodoResourceMethod find(String method) {
		return Arrays
				.stream(TodoResourceMethod.values())
				.filter(m -> m.getMethodName().equals(method))
				.findFirst()
				.get();
	}
}
