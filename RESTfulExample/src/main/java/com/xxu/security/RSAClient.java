package com.xxu.security;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.RSAPrivateKeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;

import org.apache.log4j.Logger;

/**
 * @author xxu
 * 
 * 
 *         library used by the client side, basically supports reading from a
 *         file to get the private key and decode message using private key. In
 *         order to store key, convert from privateKey-->byte[]--->Hex string
 * 
 *         note we write RSA key as modulus and exponent to a file twice, that's
 *         how Private.key is generated, if the key is generated by the open ssl
 *         command , we may need to change the function here
 * 
 */
public class RSAClient {
	final static Logger logger = Logger.getLogger(RSAClient.class);

	/**
	 * @param fileName
	 *            , where the private key is stored
	 * @return
	 * @throws IOException
	 */
	public PrivateKey readPrivateKeyFromFile(String fileName)
			throws IOException {

		FileInputStream file_input = null;
		ObjectInputStream object_input = null;

		try {

			File file = new File(fileName);
			file_input = new FileInputStream(file);
			object_input = new ObjectInputStream(file_input);

			/* read modulus and exponent from file */
			BigInteger modulus = (BigInteger) object_input.readObject();
			BigInteger exponent = (BigInteger) object_input.readObject();

			/* Get Private Key */
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

	/**
	 * @param data
	 * @param fileName
	 * @return
	 * @throws IOException
	 * 
	 *             decode the result using the private key read from the file
	 */
	public static String decryptData(byte[] data, String fileName)
			throws IOException {
		logger.info("----------------DECRYPTION STARTED------------");
		byte[] descryptedData = null;
		try {
			RSAClient temp = new RSAClient();
			PrivateKey privateKey = temp.readPrivateKeyFromFile(fileName);
			Cipher cipher = Cipher.getInstance("RSA");
			logger.info("the private key read from file is"
					+ Arrays.toString(privateKey.getEncoded()));
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			descryptedData = cipher.doFinal(data);
			logger.info("Decrypted Data: " + new String(descryptedData));
			logger.info("decyption completed");
			logger.info("----------------DECRYPTION ENDED------------");
			return new String(descryptedData);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("----------------DECRYPTION ENDED------------");
		return null;
	}

}
