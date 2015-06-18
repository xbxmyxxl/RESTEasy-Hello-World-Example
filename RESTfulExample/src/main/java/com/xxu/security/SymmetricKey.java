package com.xxu.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import com.xxu.util.ByteHexConversion;

/**
 * @author xxu
 *
 *         class for encryption and decryption of symmetric key
 */
public class SymmetricKey {
	final static Logger logger = Logger.getLogger(SymmetricKey.class);

	/* change your algorithm here */
	static String algorithm = "DES";
	public static List<String> keyList = new ArrayList<String>();

	/**
	 * @throws Exception
	 *             add a randomly generated key to the key list on the server
	 *             side
	 */
	public static void addNewKey() throws Exception {
		/* generate a new key */
		SecretKey symKey = KeyGenerator.getInstance(algorithm).generateKey();

		/* add to list */
		keyList.add(ByteHexConversion.BytesToHex(symKey.getEncoded()));
		logger.info("new key added for all"
				+ ByteHexConversion.BytesToHex(symKey.getEncoded()));

	}

	/**
	 * @throws Exception
	 *             remove a member leads to regenerate the key for the whole usr
	 *             group
	 */
	public static void removeGroupMember() throws Exception {
		logger.info("removing a member, generating new key for group member");
		addNewKey();

	}

	/**
	 * @param input
	 * @return
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws Exception
	 * 
	 * 
	 *             encrypt the data using the latest symmetric key
	 */
	public static byte[] encryptData(String input) throws InvalidKeyException,
			BadPaddingException, IllegalBlockSizeException,
			NoSuchPaddingException, NoSuchAlgorithmException, Exception {

		/* if the key list has not been instantiated, instantiated it */
		if (keyList == null || keyList.size() == 0) {
			addNewKey();
		}

		/* get the last entry in the key list */
		String lastKey = keyList.get(keyList.size() - 1);

		logger.info("----------------ENCRYPTION STARTED------------");
		logger.info("using key: " + lastKey);

		/* error checking, defensive programming :) */
		if (lastKey == null || lastKey.isEmpty()) {
			System.out.println("key should not be empty");
		}

		/* set the algorithim */
		Cipher c = Cipher.getInstance(algorithm);

		/* restore the key from byte[] */
		SecretKey pkey = new SecretKeySpec(
				(ByteHexConversion.HexToBytes(lastKey)), 0,
				(ByteHexConversion.HexToBytes(lastKey)).length, algorithm);

		c.init(Cipher.ENCRYPT_MODE, pkey);

		byte[] inputBytes = input.getBytes();

		return c.doFinal(inputBytes);
	}

	/**
	 * @param encryptionBytes
	 * @param keyString
	 * @return
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * 
	 *             decrpt the data using the keyString passed to it
	 */
	public static String decryptData(byte[] encryptionBytes, String keyString)
			throws InvalidKeyException, BadPaddingException,
			IllegalBlockSizeException, NoSuchPaddingException,
			NoSuchAlgorithmException {

		/*restore the key from key store*/
		SecretKey pkey = new SecretKeySpec(
				(ByteHexConversion.HexToBytes(keyString)), 0,
				(ByteHexConversion.HexToBytes(keyString)).length, algorithm);
		
		/* set the algorithm*/
		Cipher c = Cipher.getInstance(algorithm);
		c.init(Cipher.DECRYPT_MODE, pkey);

		byte[] decrypt = c.doFinal(encryptionBytes);

		String decrypted = new String(decrypt);

		return decrypted;
	}

	/* getter for key list */
	public static List<String> getKeyList() throws Exception {
		/*again key list should never be empty*/
		if (keyList == null || keyList.size() == 0) {
			addNewKey();
		}
		return keyList;
	}

}