package org.jboss.soa.qa.resteasy.params;

import org.jboss.soa.qa.resteasy.utils.StringUtils;

import javax.validation.constraints.Min;

public class OffsetParam {

	@Min(0L)
	private int offset;

	public OffsetParam(String value) {
		offset = StringUtils.toInt(value);
	}

	public int getOffset() {
		// RestEasy can not handle WAE by param binding, validation must be lazy
		ParamValidator.validate(this);
		return offset;
	}
}
