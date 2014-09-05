package org.jboss.soa.qa.resteasy.params;

import org.jboss.soa.qa.resteasy.utils.StringUtils;

import javax.validation.constraints.Min;

import lombok.Getter;

public class OffsetParam {

	@Min(0L)
	@Getter private int offset;

	public OffsetParam(String value) {
		offset = StringUtils.toInt(value);
		ParamValidator.validate(this);
	}
}
