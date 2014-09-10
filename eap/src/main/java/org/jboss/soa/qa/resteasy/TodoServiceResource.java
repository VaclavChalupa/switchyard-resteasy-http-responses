package org.jboss.soa.qa.resteasy;

import org.jboss.soa.qa.resteasy.params.CreateTodoParam;
import org.jboss.soa.qa.resteasy.params.LimitParam;
import org.jboss.soa.qa.resteasy.params.OffsetParam;
import org.jboss.soa.qa.resteasy.params.UpdateTodoParam;

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
			@QueryParam("limit") LimitParam limit,
			@QueryParam("offset") OffsetParam offset
	);

	@GET
	@Path("/{id:\\d+}")
	@Produces(MediaType.APPLICATION_JSON)
	Response get(@PathParam("id") Long id);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response create(CreateTodoParam todo);

	@PUT
	@Path("/{id:\\d+}")
	@Consumes(MediaType.APPLICATION_JSON)
	Response update(
			@PathParam("id") long id,
			UpdateTodoParam todo
	);

	@DELETE
	@Path("/{id:\\d+}")
	@Produces(MediaType.APPLICATION_JSON)
	Response delete(@PathParam("id") Long id);
}
