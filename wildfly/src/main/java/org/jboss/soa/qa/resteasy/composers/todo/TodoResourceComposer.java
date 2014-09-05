package org.jboss.soa.qa.resteasy.composers.todo;

import org.jboss.soa.qa.resteasy.composers.ComposerUtils;
import org.jboss.soa.qa.resteasy.metadata.Register;
import org.jboss.soa.qa.resteasy.model.ListResource;
import org.jboss.soa.qa.resteasy.model.Todo;
import org.jboss.soa.qa.resteasy.params.LimitParam;
import org.jboss.soa.qa.resteasy.params.OffsetParam;

import org.switchyard.Exchange;
import org.switchyard.ExchangeState;
import org.switchyard.HandlerException;
import org.switchyard.Message;
import org.switchyard.component.resteasy.composer.RESTEasyBindingData;
import org.switchyard.component.resteasy.composer.RESTEasyMessageComposer;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class TodoResourceComposer extends RESTEasyMessageComposer {

	@Override
	public Message compose(RESTEasyBindingData source, Exchange exchange) throws Exception {
		final Message message = super.compose(source, exchange);
		TodoResourceMethod.find(source.getOperationName()).composer(this).compose(source, message);
		return message;
	}

	@Override
	public RESTEasyBindingData decompose(Exchange exchange, RESTEasyBindingData target) throws Exception {
		if (exchange.getState().equals(ExchangeState.FAULT)) {
			HandlerException he = ComposerUtils.getAuthenticationException(exchange);
			if (he != null) {
				throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).build());
			}
			he = ComposerUtils.getAuthorizationException(exchange);
			if (he != null) {
				throw new WebApplicationException(Response.status(Response.Status.FORBIDDEN).build());
			}
			final Exception e = (Exception) exchange.getMessage().getContent();
			throw new WebApplicationException(e instanceof HandlerException ? e.getCause() : e);
		}

		target = super.decompose(exchange, target);
		TodoResourceMethod.find(target.getOperationName()).decomposer(this).decompose(target, exchange.getMessage());
		return target;
	}

	public void composeGetAll(RESTEasyBindingData source, Message message) {
		if (source.getParameters().length == 3) {
			message.getContext().setProperty(Register.TodoResource.GetAll.COMPLETED, source.getParameters()[0]);

			final LimitParam lp = (LimitParam) source.getParameters()[1];
			if (lp != null) {
				message.getContext().setProperty(Register.PaginatedResource.LIMIT, lp.getLimit());
			}

			final OffsetParam op = (OffsetParam) source.getParameters()[2];
			if (op != null) {
				message.getContext().setProperty(Register.PaginatedResource.OFFSET, op.getOffset());
			}
		}
	}

	public void composeGet(RESTEasyBindingData source, Message message) {
	}

	public void composeCreate(RESTEasyBindingData source, Message message) {
	}

	public void composeUpdate(RESTEasyBindingData source, Message message) {
		if (source.getParameters().length == 2) {
			message.setContent(source.getParameters()[1]);
			message.getContext().setProperty(Register.TodoResource.Update.ID, source.getParameters()[0]);
		}
	}

	public void composeDelete(RESTEasyBindingData source, Message message) {
	}

	public void decomposeGetAll(RESTEasyBindingData target, Message message) {
		final ListResource<Todo> lr = (ListResource<Todo>) message.getContent();
		target.setParameters(new Object[] {lr.getList()});
		target.addHeader("X-Count", ((Integer) lr.getCount()).toString());
	}

	public void decomposeGet(RESTEasyBindingData target, Message message) {
	}

	public void decomposeCreate(RESTEasyBindingData target, Message message) {
		final Todo todo = (Todo) message.getContent();
		target.setStatusCode(Response.Status.CREATED.getStatusCode());
		target.addHeader("Location", "/api/v1/todos/" + todo.getId().toString());
	}

	public void decomposeUpdate(RESTEasyBindingData target, Message message) {
		message.setContent(null);
		target.setParameters(new Object[] {});
		target.setStatusCode(Response.Status.NO_CONTENT.getStatusCode());
	}

	public void decomposeDelete(RESTEasyBindingData target, Message message) {
		message.setContent(null);
		target.setParameters(new Object[] {});
		target.setStatusCode(Response.Status.NO_CONTENT.getStatusCode());
	}
}
