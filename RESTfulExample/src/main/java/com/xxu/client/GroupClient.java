package com.xxu.client;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;

import com.xxu.logger.HelloExample;
import com.xxu.security.RSAClient;
import com.xxu.security.SymmetricKey;
import com.xxu.util.ByteHexConversion;

public class GroupClient {
	final Logger logger = Logger.getLogger(GroupClient.class);
	private List<String> keyList = new ArrayList<String>();
	private Client c;
	private String id;
	private String fileName;

	public static void main(String[] args) throws Exception {
		GroupClient a = new GroupClient("1", "Private.key");
		a.getReply();
		SymmetricKey.removeGroupMember();
	 	a.getReply();
	}

	public GroupClient(String id, String fileName) {
		this.id = id;
		this.fileName = fileName;
	}

	private void updateKeyList() {
		try {
			keyList.clear();
			Client c = new Client("/group/" + id);
			String codeWords = c.getReply();
			//logger.info("received from server"+codeWords);
			String[] codePerClient = codeWords.split(";");
			String decodeResult = "";

			for (int i = codePerClient.length-1; i >= 0; i--) {

				decodeResult = RSAClient.decryptData(
						ByteHexConversion.HexToBytes(codePerClient[i]),
						fileName);

				if (decodeResult != null) {
					logger.info("adding key %s to key list " + decodeResult);
					keyList.add(decodeResult);
				}

			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			Assert.fail();

		}
	}

	public String getReply() {
		try {
			if (keyList == null || keyList.isEmpty()) {
				logger.info("initialize the key ");
				this.updateKeyList();
			}

			if (keyList != null && !keyList.isEmpty()) {
				logger.info("requesting data from server");

				c = new Client("/group/testdata");
				String data = c.getReply();
				String decodeResult = "";
				System.out.println("before loop");
				System.out.println("key list" + keyList);
				for (int i = 0; i < keyList.size(); i++) {
					System.out.println("here inside loop");
					decodeResult += SymmetricKey.decryptData(
							ByteHexConversion.HexToBytes(data), keyList.get(i).replaceAll("\\s+",""));

					if (decodeResult != null && !decodeResult.isEmpty()) {
						logger.info("decoding data using key " + i
								+ " success!");
						logger.info("decode result is : " + decodeResult);
						return decodeResult;
					}
					logger.info("decoding data using key " + i
							+ " failed, retrying;"+decodeResult);
				}

				if (decodeResult == null || decodeResult.isEmpty()) {
					this.updateKeyList();
					for (int i = 0; i < keyList.size(); i++) {

						decodeResult += SymmetricKey.decryptData(
								ByteHexConversion.HexToBytes(data),
								keyList.get(i));

						if (decodeResult != null && !decodeResult.isEmpty()) {
							logger.info("After updating keys, decode successfully.");
							logger.info("decode result is : " + decodeResult);
							return decodeResult;

						} else {
							if (i == keyList.size() - 1)
								logger.info("After updating keys, still can not decode.");
						}
					}

				}

			} else {
				logger.info("no group key found ");
			}
		} catch (Exception e) {

			e.printStackTrace();
			Assert.fail();

		}
		return null;
	}
}
