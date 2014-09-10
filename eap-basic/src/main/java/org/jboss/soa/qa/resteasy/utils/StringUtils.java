package org.jboss.soa.qa.resteasy.utils;

public final class StringUtils {

	public static int toInt(String value) {
		return toInt(value, 0);
	}

	public static int toInt(String value, int defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	private StringUtils() {
	}
}
