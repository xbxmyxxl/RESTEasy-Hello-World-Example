package com.xxu.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;

import com.xxu.security.RSAServer;
import com.xxu.security.RandomStringGenerator;
import java.net.URL;
import org.xml.sax.SAXException;
import java.lang.ClassLoader;

public class RSAClient {

	private static final String PRIVATE_KEY_FILE = "Private.key";

	public static void main(String[] args) throws IOException,
			NoSuchAlgorithmException, NoSuchProviderException {
		String to_be_encoded = "362195C2C0905D4327AC7C1518066C"
				+ "AC84A5BD237AE709706DBBD081889D36E0AA060C4A30975EA47E2F3D282D0C97650B9CF30294BA370909324C6D"
				+ "118F48D511CF6DA9E7E0403FE34C7FA3B1ACBBAAE0A69E5E3C321FB4378DC327CCBF6CDCD1B86691763F697840"
				+ "2799CD2AAB7C58850F2C0114A117156F2D0A0087512337419509F7649D5A30FB404D861EAA2BB4F0438AC89B316"
				+ "55A374BE121B014EDB5DC1C0674EF8445EF6ED4B9AEA6637BD2AD831A5897E27F9B85EE120FEEBFC7D765E285AC"
				+ "64BF17C8561C1EE428FF766DC2E4314CCA094675C76713B0DD6049427357D74E7752177847D3F0BDC75357A588AF"
				+ "070BED47D905768BDB3327ADC722";
		RSAClient temp  = new RSAClient();
		temp.decryptData(RSAServer.HexToBytes(to_be_encoded));
	}

	public PrivateKey readPrivateKeyFromFile(String fileName)
			throws IOException {
		FileInputStream file_input = null;
		ObjectInputStream object_input = null;
		try {
			ClassLoader classLoader = this.getClass().getClassLoader();

			URL resource = classLoader.getResource(PRIVATE_KEY_FILE);
			File file = new File("private.key");
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

	public void decryptData(byte[] data) throws IOException {
		System.out.println("\n----------------DECRYPTION STARTED------------");
		byte[] descryptedData = null;

		try {
			PrivateKey privateKey = readPrivateKeyFromFile(PRIVATE_KEY_FILE);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			descryptedData = cipher.doFinal(data);
			System.out.println("Decrypted Data: " + new String(descryptedData));

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("----------------DECRYPTION COMPLETED------------");
	}

}
