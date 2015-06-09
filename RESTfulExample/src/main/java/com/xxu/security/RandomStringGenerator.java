package com.xxu.security;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RandomStringGenerator {

	private static SecureRandom random = new SecureRandom();

	public static String StringGenerator () {
		return new BigInteger(130, random).toString(32);
	}

}
