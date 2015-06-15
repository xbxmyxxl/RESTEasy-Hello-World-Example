package com.xxu.security;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.xxu.util.ByteHexConversion;
public class SymmetricKey {

	static String algorithm = "DES";
	public static final String key = "3D9D40976829CD98";

	public static void main(String[] args) throws Exception {

		SecretKey symKey = KeyGenerator.getInstance(algorithm).generateKey();
		System.out.println(ByteHexConversion.BytesToHex(symKey.getEncoded()));

		Cipher c = Cipher.getInstance(algorithm);

		byte[] encryptionBytes = encryptData("Hello World");

		System.out
				.println("Decrypted: " + decryptData(encryptionBytes, key));
	}

	private static byte[] encryptData(String input)
			throws InvalidKeyException, BadPaddingException,
			IllegalBlockSizeException, NoSuchPaddingException,
			NoSuchAlgorithmException {
		
		Cipher c = Cipher.getInstance(algorithm);
		SecretKey pkey = new SecretKeySpec((ByteHexConversion.HexToBytes(key)), 0, (ByteHexConversion.HexToBytes(key)).length, algorithm);

		c.init(Cipher.ENCRYPT_MODE, pkey);

		byte[] inputBytes = input.getBytes();

		return c.doFinal(inputBytes);
	}

	private static String decryptData(byte[] encryptionBytes, String keyString)
			throws InvalidKeyException,
			BadPaddingException, IllegalBlockSizeException,
			NoSuchPaddingException, NoSuchAlgorithmException {
		
		
		SecretKey pkey = new SecretKeySpec((ByteHexConversion.HexToBytes(keyString)), 0, (ByteHexConversion.HexToBytes(keyString)).length, algorithm);
		Cipher c = Cipher.getInstance(algorithm);
		c.init(Cipher.DECRYPT_MODE, pkey);

		byte[] decrypt = c.doFinal(encryptionBytes);

		String decrypted = new String(decrypt);

		return decrypted;
	}
}