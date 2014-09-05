package org.jboss.soa.qa.resteasy.composers;

import org.switchyard.Message;
import org.switchyard.component.resteasy.composer.RESTEasyBindingData;

@FunctionalInterface
public interface DecomposerFunction {

	void decompose(RESTEasyBindingData source, Message message);
}
