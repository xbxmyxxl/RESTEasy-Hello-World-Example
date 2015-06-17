package com.xxu.security;

import java.security.InvalidKeyException;
import java.security.Key;
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

import com.xxu.rest.GroupCommService;
import com.xxu.util.ByteHexConversion;

public class SymmetricKey {
	final static Logger logger = Logger.getLogger(SymmetricKey.class);
	static String algorithm = "DES";
	public static final String key = "3D9D40976829CD98";
	public static List<String> keyList = new ArrayList<String>();
	//final static Logger logger = Logger.getLogger(SymmetricKey.class);

	public static void main(String[] args) throws Exception {


		SymmetricKey.addNewKey();
		System.out.println(SymmetricKey.keyList);

		SymmetricKey.addNewKey();
		System.out.println(SymmetricKey.keyList);
		SymmetricKey.encryptData("test");
	}
	
	
	public static void addNewKey() throws Exception {
		SecretKey symKey = KeyGenerator.getInstance(algorithm).generateKey();
		keyList.add(ByteHexConversion.BytesToHex(symKey.getEncoded()));
		logger.info("new key added for all"+ ByteHexConversion.BytesToHex(symKey.getEncoded()));

	}
	
	public static void removeGroupMember() throws Exception {
		logger.info("removing a member, generating new key for group member");
		addNewKey();

	}

	public static byte[] encryptData(String input) throws InvalidKeyException,
			BadPaddingException, IllegalBlockSizeException,
			NoSuchPaddingException, NoSuchAlgorithmException, Exception {
		
		if (keyList == null ||keyList.size() == 0) {
			addNewKey();
		}
		
		String lastKey = keyList.get(keyList.size() - 1);
		System.out.println("****************encrypt using key*****************" + lastKey);
		if (lastKey == null || lastKey.isEmpty()) {
			System.out.println("key should not be empty");
		}

		Cipher c = Cipher.getInstance(algorithm);
		SecretKey pkey = new SecretKeySpec((ByteHexConversion.HexToBytes(lastKey)),
				0, (ByteHexConversion.HexToBytes(key)).length, algorithm);

		c.init(Cipher.ENCRYPT_MODE, pkey);

		byte[] inputBytes = input.getBytes();

		return c.doFinal(inputBytes);
	}

	public static String decryptData(byte[] encryptionBytes, String keyString)
			throws InvalidKeyException, BadPaddingException,
			IllegalBlockSizeException, NoSuchPaddingException,
			NoSuchAlgorithmException {

		SecretKey pkey = new SecretKeySpec(
				(ByteHexConversion.HexToBytes(keyString)), 0,
				(ByteHexConversion.HexToBytes(keyString)).length, algorithm);
		Cipher c = Cipher.getInstance(algorithm);
		c.init(Cipher.DECRYPT_MODE, pkey);

		byte[] decrypt = c.doFinal(encryptionBytes);

		String decrypted = new String(decrypt);

		return decrypted;
	}
	public static List<String> getKeyList() throws Exception {
		if(keyList==null||keyList.size()==0)
		{
			addNewKey();
		}
		return keyList;
	}
	public static void setKeyList(List<String> keyList) {
		SymmetricKey.keyList = keyList;
	}
}