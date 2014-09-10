package org.jboss.soa.qa.resteasy.model;

import org.jboss.soa.qa.resteasy.validation.group.Create;
import org.jboss.soa.qa.resteasy.validation.group.Update;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class Todo implements Serializable {

	private static final long serialVersionUID = 4566883394691043302L;

	@Null(groups = Create.class) @NotNull(groups = Update.class)
	@Getter @Setter private Long id;

	@Getter @Setter private String text;

	@Getter @Setter private boolean completed;
}
