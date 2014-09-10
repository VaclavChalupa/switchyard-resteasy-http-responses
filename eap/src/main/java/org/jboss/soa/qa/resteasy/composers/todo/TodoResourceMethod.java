package org.jboss.soa.qa.resteasy.composers.todo;

import org.switchyard.Message;
import org.switchyard.component.resteasy.composer.RESTEasyBindingData;

public enum TodoResourceMethod {

	GET_ALL("getAll") {

		@Override
		public void compose(RESTEasyBindingData source, Message message) {
			TodoResourceComposer.composeGetAll(source, message);
		}

		@Override
		public void decompose(RESTEasyBindingData target, Message message) {
			TodoResourceComposer.decomposeGetAll(target, message);
		}
	},
	GET("get") {

		@Override
		public void compose(RESTEasyBindingData source, Message message) {
			TodoResourceComposer.composeGet(source, message);
		}

		@Override
		public void decompose(RESTEasyBindingData target, Message message) {
			TodoResourceComposer.decomposeGet(target, message);
		}
	},
	CREATE("create") {

		@Override
		public void compose(RESTEasyBindingData source, Message message) {
			TodoResourceComposer.composeCreate(source, message);
		}

		@Override
		public void decompose(RESTEasyBindingData target, Message message) {
			TodoResourceComposer.decomposeCreate(target, message);
		}
	},
	UPDATE("update") {

		@Override
		public void compose(RESTEasyBindingData source, Message message) {
			TodoResourceComposer.composeUpdate(source, message);
		}

		@Override
		public void decompose(RESTEasyBindingData target, Message message) {
			TodoResourceComposer.decomposeUpdate(target, message);
		}
	},
	DELETE("delete") {

		@Override
		public void compose(RESTEasyBindingData source, Message message) {
			TodoResourceComposer.composeDelete(source, message);
		}

		@Override
		public void decompose(RESTEasyBindingData target, Message message) {
			TodoResourceComposer.decomposeDelete(target, message);
		}
	};

	private String methodName;

	TodoResourceMethod(String methodName) {
		this.methodName = methodName;
	}

	public String getMethodName() {
		return methodName;
	}

	public abstract void compose(RESTEasyBindingData source, Message message);
	public abstract void decompose(RESTEasyBindingData target, Message message);

	public static TodoResourceMethod find(String method) {
		for (TodoResourceMethod m : TodoResourceMethod.values()) {
			if (m.getMethodName().equals(method)) {
				return m;
			}
		}
		return null;
	}
}
