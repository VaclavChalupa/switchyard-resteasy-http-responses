package org.jboss.soa.qa.resteasy.metadata.todo;

import org.jboss.soa.qa.resteasy.metadata.PaginationMetadataProducer;
import org.jboss.soa.qa.resteasy.metadata.Register;

public class TodoResourceMetadataProducer extends PaginationMetadataProducer {

	public GetAllMetadata getGetAllMetadata() {
		final Boolean completed = (Boolean) getPropertyValue(Register.TodoResource.GetAll.COMPLETED);
		final GetAllMetadata md = new GetAllMetadata(completed);
		setPagination(md);
		return md;
	}

	public UpdateMetadata getUpdateMetadata() {
		final UpdateMetadata md = new UpdateMetadata((Long) getPropertyValue(Register.TodoResource.Update.ID));
		return md;
	}
}
