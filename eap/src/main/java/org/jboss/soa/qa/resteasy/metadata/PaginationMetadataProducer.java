package org.jboss.soa.qa.resteasy.metadata;

public abstract class PaginationMetadataProducer extends ResourceMetadataProducer {

	protected void setPagination(PaginationMetadata filter) {
		final Integer limit = (Integer) getPropertyValue(Register.PaginatedResource.LIMIT);
		if (limit != null) {
			filter.setLimit(limit);
		}
		final Integer offset = (Integer) getPropertyValue(Register.PaginatedResource.OFFSET);
		if (offset != null) {
			filter.setOffset(offset);
		}
	}
}
