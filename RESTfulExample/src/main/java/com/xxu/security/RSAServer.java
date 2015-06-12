package com.xxu.security;
import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;

import java.util.Arrays;

import com.xxu.database.PublicKeyDatabase;
import com.xxu .util.*
;//current thing working on 
public class RSAServer {
	private static final String PUBLIC_KEY_FILE = "Public.key";
	static String  public_key_test = "30820122300D06092A864886F70D01010105000382010F003082010A02820101009E421B1618BA"
			+ "641A6D19BC51107DA739A038F2D4106D1FD3853070F8956EEC74A2181AB9D6CBF8A45999CB60F0406EC72A5ED146DFB9CAA197501E0B6D43"
			+ "47308F3E2A3191553FEC62FBFFD3A4F63A0787C85966240497820BF2F73AD14F6AD2EA1763099104C7C85E295F45897EAEF3BA536708C6"
			+ "7B18CD53AC3B31727F315C63C100FB5E6DAC197389F29C4F95AFD1F49D285FE2AD31A9E1D30CD5CA8321E1E82C0995EB4816997922FB8"
			+ "7C69EAA9EC66C8B46813F722091F3448FB9971C7AFC310896594EBBE656A7BC180A2C15238915FD7A780A4B15A057C3243FFD6460FDB"
			+ "DC394B23FC95B0611BE4FDEE3532A515F6485216D3CD0D2A77FB1986342930203010001";

	public static void main(String[] args) throws IOException,
			NoSuchAlgorithmException, NoSuchProviderException {
		String test_string = RandomStringGenerator.StringGenerator();
		System.out.println(test_string);
		PublicKey pubKey = readPublicKeyFromFile(PUBLIC_KEY_FILE);
		System.out.println(Arrays.toString(pubKey.getEncoded()));
		System.out.println(Arrays.toString(ByteHexConversion.HexToBytes(RSAServer.public_key_test)));
		
		System.out.println("comparing two strings :"+PublicKeyDatabase.getPublicKeyById("1").equals(public_key_test));
		
		byte[] encryptedData1 = RSAServer.encryptData(test_string,public_key_test);
		byte[] encryptedData2 = RSAServer.encryptData(test_string,"1");
		System.out.println(ByteHexConversion.BytesToHex(encryptedData1));
		System.out.println(ByteHexConversion.BytesToHex(encryptedData2));
	}


	public static byte[] encryptData(String data,String id) throws IOException {
		System.out.println("\n----------------ENCRYPTION STARTED------------");
		
		System.out.println("Data Before Encryption :" + data);
		byte[] dataToEncrypt = data.getBytes();
		byte[] encryptedData = null;
		try {
			PublicKey pubKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(ByteHexConversion.HexToBytes(PublicKeyDatabase.getPublicKeyById(id))));	
			System.out.println(Arrays.toString(pubKey.getEncoded()));
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			encryptedData = cipher.doFinal(dataToEncrypt);
			System.out.println("Encryted Data: " + encryptedData);
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		System.out.println("----------------ENCRYPTION COMPLETED------------");		
		return encryptedData;
	}
	
	public static PublicKey readPublicKeyFromFile(String fileName)
			throws IOException {
		FileInputStream file_input = null;
		ObjectInputStream object_input = null;
		try {
			file_input = new FileInputStream(new File(fileName));
			object_input = new ObjectInputStream(file_input);

			BigInteger modulus = (BigInteger) object_input.readObject();
			BigInteger exponent = (BigInteger) object_input.readObject();

			// Get Public Key
			RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(modulus,
					exponent);
			KeyFactory fact = KeyFactory.getInstance("RSA");
			PublicKey publicKey = fact.generatePublic(rsaPublicKeySpec);

			return publicKey;

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

}