package org.jboss.soa.qa.resteasy.metadata.todo;

import org.jboss.soa.qa.resteasy.metadata.PaginationMetadata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class GetAllMetadata extends PaginationMetadata {

	@Getter private Boolean completed;
}
