package org.jboss.soa.qa.resteasy.metadata;

import org.switchyard.Context;
import org.switchyard.Property;

import javax.inject.Inject;

public abstract class ResourceMetadataProducer {

	@Inject
	private Context context;

	protected Object getPropertyValue(String name) {
		for (Property p : context.getProperties()) {
			if (name.equals(p.getName())) {
				return p.getValue();
			}
		}
		return null;
	}
}
