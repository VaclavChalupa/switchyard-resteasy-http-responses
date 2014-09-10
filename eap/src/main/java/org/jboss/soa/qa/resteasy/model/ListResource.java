package org.jboss.soa.qa.resteasy.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ListResource<T> {

	@Getter private List<T> list;
	@Getter private int count;
}
