package org.jboss.soa.qa.resteasy.composers;

import org.jboss.soa.qa.resteasy.Register;

import org.switchyard.Exchange;
import org.switchyard.Message;
import org.switchyard.component.resteasy.composer.RESTEasyBindingData;
import org.switchyard.component.resteasy.composer.RESTEasyMessageComposer;

public class TodoResourceComposer extends RESTEasyMessageComposer {

	@Override
	public Message compose(RESTEasyBindingData source, Exchange exchange) throws Exception {
		final Message message = super.compose(source, exchange);
		if ("getAll".equals(source.getOperationName()) && source.getParameters().length == 3) {
			message.getContext().setProperty(Register.TodoResource.GetAll.COMPLETED, source.getParameters()[0]);
			message.getContext().setProperty(Register.PaginatedResource.LIMIT, source.getParameters()[1]);
			message.getContext().setProperty(Register.PaginatedResource.OFFSET, source.getParameters()[2]);
		} else if ("update".equals(source.getOperationName()) && source.getParameters().length == 2) {
			message.setContent(source.getParameters()[1]);
			message.getContext().setProperty(Register.TodoResource.Update.ID, source.getParameters()[0]);
		}
		return message;
	}
}
