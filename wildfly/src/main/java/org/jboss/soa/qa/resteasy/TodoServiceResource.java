package org.jboss.soa.qa.resteasy;

import org.jboss.soa.qa.resteasy.model.Todo;
import org.jboss.soa.qa.resteasy.validation.ParamName;
import org.jboss.soa.qa.resteasy.validation.group.Create;
import org.jboss.soa.qa.resteasy.validation.group.Update;

import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/todos")
public interface TodoServiceResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	Response getAll(
			@QueryParam("completed") Boolean completed,
			@QueryParam("limit") @Range(min = 0L, max = 10L) @ParamName("limit") Integer limit,
			@QueryParam("offset") @Min(0L) @ParamName("offset") Integer offset
	);

	@GET
	@Path("/{id:\\d+}")
	@Produces(MediaType.APPLICATION_JSON)
	Response get(@PathParam("id") Long id);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response create(@NotNull @Valid @ConvertGroup(from = Default.class, to = Create.class) Todo todo);

	@PUT
	@Path("/{id:\\d+}")
	@Consumes(MediaType.APPLICATION_JSON)
	Response update(
			@PathParam("id") long id,
			@NotNull @Valid @ConvertGroup(from = Default.class, to = Update.class) Todo todo
	);

	@DELETE
	@Path("/{id:\\d+}")
	@Produces(MediaType.APPLICATION_JSON)
	Response delete(@PathParam("id") Long id);
}
