package com.ctv.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class UniqueStringUtils {
	public static String randomOrderId() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuMMdd");
		LocalDate localDate = LocalDate.now();
		String strDate = dtf.format(localDate);
		char[] random = new char[8];
		char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
		Random r = new Random();
		for (int i = 0; i < 8; i++) {
			random[i] = chars[r.nextInt(chars.length)];
		}
		String strRandom = new String(random);
		return new String(strDate + strRandom);
	}

	/**
	 * Get a random uuid which each character may be in one of 'abcdef0123456789'
	 * @param length the length of random uuid to generate
	 */
	public static String randomUUID(int length) {
		char[] random = new char[length];
		char[] chars = "abcdef1234567890".toCharArray();
		Random r = new Random();
		for (int i = 0; i < length; i++) {
			if (i % 8 == 0 && i>0) random[i] = '-';
			else
				random[i] = chars[r.nextInt(chars.length)];
		}
		return new String(random);
	}
	public static String otp(){
		char[] random = new char[6];
		char[] chars = "1234567890".toCharArray();
		Random r = new Random();
		for (int i = 0; i < 6; i++) {
			random[i] = chars[r.nextInt(chars.length)];
		}
		return new String(random);
	}
}
