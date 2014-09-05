package org.jboss.soa.qa.resteasy.metadata;

import lombok.Getter;
import lombok.Setter;

public class PaginationMetadata {

	@Getter @Setter private int offset = 0;
	@Getter @Setter private int limit = 10;
}
