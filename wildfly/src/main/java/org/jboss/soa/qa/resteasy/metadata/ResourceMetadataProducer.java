package org.jboss.soa.qa.resteasy.metadata;

import java.util.Optional;

import org.switchyard.Context;
import org.switchyard.Property;
import javax.inject.Inject;

public abstract class ResourceMetadataProducer {

	@Inject
	private Context context;

	protected Object getPropertyValue(String name) {
		Optional<Property> property = context.getProperties()
				.stream()
				.filter(p -> name.equals(p.getName()))
				.findFirst();

		if (property.isPresent()) {
			return property.get().getValue();
		}
		return null;
	}
}
