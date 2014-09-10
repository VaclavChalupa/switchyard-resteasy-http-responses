package org.jboss.soa.qa.resteasy.metadata;

/**
 * Register of metadata params.
 */
public class Register {

	/**
	 * Metadata of params for all paginated resources.
	 */
	public static class PaginatedResource {
		public static final String LIMIT = "PaginatedResource.limit";
		public static final String OFFSET = "PaginatedResource.offset";
	}

	/**
	 * Metadata of params for TodoServiceResource.
	 */
	public static class TodoResource {

		/**
		 * Metadata of params for getAll resource.
		 */
		public static class GetAll {
			public static final String COMPLETED = "TodoServiceResource.getAll.completed";
		}

		/**
		 * Metadata of params for update resource.
		 */
		public static class Update {
			public static final String ID = "TodoServiceResource.update.id";
		}
	}
}
