package com.ctv.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CaseUtils {
	public static String convert2KebabCase(String input) {
		Matcher matcher =
				Pattern.compile("[\\p{L}\\p{Nd}]+",Pattern.UNICODE_CHARACTER_CLASS).matcher(input);
		List<String> matched = new ArrayList<>();
		while (matcher.find()) {
			matched.add(matcher.group(0));
		}
		return matched.stream()
				.map(String::toLowerCase)
				.collect(Collectors.joining("-"));
	}
}
