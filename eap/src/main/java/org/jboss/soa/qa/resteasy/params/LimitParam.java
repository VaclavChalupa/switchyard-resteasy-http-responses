package org.jboss.soa.qa.resteasy.params;

import org.jboss.soa.qa.resteasy.utils.StringUtils;

import org.hibernate.validator.constraints.Range;

public class LimitParam {

	private static final long MIN = 0L;
	private static final long MAX = 10L;

	@Range(min = MIN, max = MAX)
	private int limit;

	public LimitParam(String value) {
		limit = StringUtils.toInt(value);
	}

	public int getLimit() {
		// RestEasy can not handle WAE by param binding, validation must be lazy
		ParamValidator.validate(this);
		return limit;
	}
}
