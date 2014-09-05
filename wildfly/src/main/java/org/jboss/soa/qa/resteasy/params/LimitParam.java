package org.jboss.soa.qa.resteasy.params;

import org.jboss.soa.qa.resteasy.utils.StringUtils;

import org.hibernate.validator.constraints.Range;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import lombok.Getter;

public class LimitParam {

	private static final long MIN = 0L;
	private static final long MAX = 10L;

	@Context
	private HttpHeaders headers;

	@Range(min = MIN, max = MAX)
	@Getter private int limit;

	public LimitParam(String value) {
		limit = StringUtils.toInt(value);
		ParamValidator.validate(this);
	}
}
