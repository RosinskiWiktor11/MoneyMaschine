package com.victor.utils;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Wiktor
 *
 */
public class DataConverterUT {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void stringToDecimal1() {
		boolean result = false;
		int value1 = com.victor.utils.DataConverter.stringToBigDecimal("123",
				".").intValue();
		if (value1 == 123)
			result = true;
		assertTrue(result);
	}

	@Test
	public void stringToDecimal2() {
		boolean result = false;
		double value1 = Math.rint(com.victor.utils.DataConverter
				.stringToBigDecimal("123.14", ".").doubleValue());
		double value2 = Math.rint(123.14);
		if (value1 == value2)
			result = true;
		assertTrue(result);
	}

	@Test
	public void stringToDecimal3() {
		boolean result = false;
		double value1 = Math.rint(com.victor.utils.DataConverter
				.stringToBigDecimal("123,1458", ",").doubleValue());
		double value2 = Math.rint(123.1458);
		if (value1 == value2)
			result = true;
		assertTrue(result);
	}

	@Test
	public void stringToDecimal4() {
		boolean result = false;
		double value1 = Math.rint(com.victor.utils.DataConverter
				.stringToBigDecimal("123q1458", "q").doubleValue());
		double value2 = Math.rint(123.1458);
		if (value1 == value2)
			result = true;
		assertTrue(result);
	}

	@Test
	public void stringToDecimalWithException() {
		thrown.expect(RuntimeException.class);
		thrown.expectMessage("It is no number!");
		com.victor.utils.DataConverter.stringToBigDecimal("123,145,8", ",");

	}
}
