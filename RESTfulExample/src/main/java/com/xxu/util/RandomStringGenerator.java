package com.xxu.util;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author xxu
 *
 *         generate random string as token, use secure random, to make the
 *         string hard to guess
 */
public class RandomStringGenerator {

	private static SecureRandom random = new SecureRandom();

	public static String StringGenerator() {
		return new BigInteger(130, random).toString(32);
	}

}
