package com.xxu.security;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

public class SymmetricKey {

	static String algorithm = "DESede";

	public static void main(String[] args) throws Exception {

		Key symKey = KeyGenerator.getInstance(algorithm).generateKey();
		System.out.println(symKey.getEncoded());

		Cipher c = Cipher.getInstance(algorithm);

		byte[] encryptionBytes = encryptData("texttoencrypt", symKey);

		System.out
				.println("Decrypted: " + decryptData(encryptionBytes, symKey));
	}

	private static byte[] encryptData(String input, Key pkey)
			throws InvalidKeyException, BadPaddingException,
			IllegalBlockSizeException, NoSuchPaddingException,
			NoSuchAlgorithmException {
		
		Cipher c = Cipher.getInstance(algorithm);

		c.init(Cipher.ENCRYPT_MODE, pkey);

		byte[] inputBytes = input.getBytes();

		return c.doFinal(inputBytes);
	}

	private static String decryptData(byte[] encryptionBytes, Key pkey)
			throws InvalidKeyException,
			BadPaddingException, IllegalBlockSizeException,
			NoSuchPaddingException, NoSuchAlgorithmException {

		Cipher c = Cipher.getInstance(algorithm);
		c.init(Cipher.DECRYPT_MODE, pkey);

		byte[] decrypt = c.doFinal(encryptionBytes);

		String decrypted = new String(decrypt);

		return decrypted;
	}
}