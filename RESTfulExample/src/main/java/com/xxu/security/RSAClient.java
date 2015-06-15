package com.xxu.security;

import java.io.File;

import com.xxu.security.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.spec.RSAPrivateKeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;

import com.xxu.client.Client;
import com.xxu.util.*;

public class RSAClient {

	private static final String PRIVATE_KEY_FILE = "Private.key";

	public static void main(String[] args) throws IOException,
			NoSuchAlgorithmException, NoSuchProviderException,Exception {
		
		Client c  = new Client("/auth/usr/2/");
		//c.getReply();
		String encode_result = ByteHexConversion.BytesToHex(RSAServer.encryptData("heelo world","2"));
		String decode_result = RSAClient.decryptData(ByteHexConversion.HexToBytes(encode_result),PRIVATE_KEY_FILE);
		System.out.println("decrpted result:  " + decode_result);
		
		c  = new Client("/auth/testPost");
		System.out.println(c.clientPost(decode_result));
		//System.out.println(c.getStatus());
		//c.getReply();
		
		
	}
	public PrivateKey readPrivateKeyFromFile(String fileName)
			throws IOException {
		FileInputStream file_input = null;
		ObjectInputStream object_input = null;
		try {
			//ClassLoader classLoader = this.getClass().getClassLoader();
			//URL resource = classLoader.getResource(fileName);
			File file = new File(fileName);
			file_input = new FileInputStream(file);
			object_input = new ObjectInputStream(file_input);

			BigInteger modulus = (BigInteger) object_input.readObject();
			BigInteger exponent = (BigInteger) object_input.readObject();

			// Get Private Key
			RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(
					modulus, exponent);
			KeyFactory fact = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = fact.generatePrivate(rsaPrivateKeySpec);

			return privateKey;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (object_input != null) {
				object_input.close();
				if (file_input != null) {
					file_input.close();
				}
			}
		}
		return null;
	}

	public static String decryptData(byte[] data,String fileName) throws IOException {
		System.out.println("\n----------------DECRYPTION STARTED------------");
		byte[] descryptedData = null;
		try {
			RSAClient temp= new RSAClient();
			PrivateKey privateKey = temp.readPrivateKeyFromFile(fileName);
			Cipher cipher = Cipher.getInstance("RSA");
			System.out.println(Arrays.toString(privateKey.getEncoded()));
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			descryptedData = cipher.doFinal(data);
			System.out.println("Decrypted Data: " + new String(descryptedData));
			System.out.println("----------------DECRYPTION COMPLETED------------");
			return new String(descryptedData);

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("----------------DECRYPTION COMPLETED------------");
		return null;
	}

}
