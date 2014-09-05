package org.jboss.soa.qa.resteasy.utils;

public final class StringUtils {

	public static int toInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	private StringUtils() {
	}
}
