package com.cor.guava.cache.util;

/**
 * Throwaway Log Helper - just for demo purposes to make the logging statements
 * stand out.
 */
public class LogHelper {

	public static String formatString(final String message) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n****************************");
		sb.append("\n* ");
		sb.append(message);
		sb.append("\n****************************");
		return sb.toString();
	}

}