package com.victor.utils;

import java.math.BigDecimal;

/**
 * @author Wiktor konwertuje dane
 */
public class DataConverter {

	/**
	 * @param number
	 *            wartosc liczbowa
	 * @param delim
	 *            String jako delimiter - znak rozdzielajacy czesc calkowita, od
	 *            czesci dziesiatnych
	 * @return
	 */
	public static BigDecimal stringToBigDecimal(String number, String delim) {
		BigDecimal bigDecimal;
		if (number.indexOf(delim) != number.lastIndexOf(delim)) {
			throw new RuntimeException("It is no number!");
		}

		if (delim.equals(".")) {
			bigDecimal = new BigDecimal(number);
		} else {
			String num = number.replaceFirst(delim, ".");
			bigDecimal = new BigDecimal(num);
		}
		return bigDecimal;
	}

}
