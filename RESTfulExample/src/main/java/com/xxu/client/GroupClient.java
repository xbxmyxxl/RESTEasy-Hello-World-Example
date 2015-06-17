package com.xxu.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.xxu.security.RSAClient;
import com.xxu.security.SymmetricKey;
import com.xxu.util.ByteHexConversion;

/**
 * @author xxu
 *
 *         client for GroupCommService
 * 
 *         Able to update key list based on encoded text form the server, decode
 *         data using keys from the key list
 */
public class GroupClient {
	final Logger logger = Logger.getLogger(GroupClient.class);
	/**
	 * KeyList keep all the used key in memory, so that when there is encoded
	 * test , it will iterate the list to trying to decode text using each key
	 * in the keylist
	 */

	private List<String> keyList = new ArrayList<String>();

	private Client c;
	/**
	 * id defines which usr it is, help server find the public key from database
	 * for the client.
	 */
	private String id;

	/**
	 * fileName defines where is the private key stored, together with id, the
	 * public key and the private key for this client is known
	 */
	private String fileName;

	public GroupClient(String id, String fileName) {
		this.id = id;
		this.fileName = fileName;
	}

	/**
	 * method to update the keyList for the client. Key may be outdated, because
	 * a user is removed from the group, and a new key for the whole group is
	 * generated, the server will always encode any information with the most
	 * recent key. Should only be called if error occurred in decoding.
	 */
	public void updateKeyList() {
		try {
			keyList.clear();

			/* connect to a web service to get the key list */
			Client c = new Client("/group/" + id);

			String codeWords = c.getReply();
			logger.info("received from server" + codeWords);

			/* Split the string received from server by ; */

			String[] codePerClient = codeWords.split(";");
			String decodeResult = "";

			/* decode each key using private key */
			for (int i = codePerClient.length - 1; i >= 0; i--) {

				decodeResult = RSAClient.decryptData(
						ByteHexConversion.HexToBytes(codePerClient[i]),
						fileName);

				if (decodeResult != null) {

					logger.info("adding key " + decodeResult + " "
							+ ((codePerClient.length - 1) - i) + "/"
							+ (codePerClient.length - 1));

					keyList.add(decodeResult);

				}

			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();

		}
	}

	/**
	 * @return the decoded result of the string received from server, null if
	 *         decoding is not successful
	 * 
	 *         iterate through the keylist trying to decode the message
	 */
	public String readResponseFromServer() {
		try {
			if (keyList != null && !keyList.isEmpty()) {

				logger.info("requesting data from server");

				/* connect to url testdata, to get the encoded string */
				c = new Client("/group/testdata");
				String data = c.getReply();

				String decodeResult = "";
				logger.info("decoding with key list:" + keyList);

				/* iterate through the keylist to decode the data */
				for (int i = 0; i < keyList.size(); i++) {
					try {
						/*
						 * try to decode by passing the data and the key to
						 * function form SymmetricKey class
						 */
						decodeResult += SymmetricKey.decryptData(
								ByteHexConversion.HexToBytes(data), keyList
										.get(i).replaceAll("\\s+", ""));

						if (decodeResult != null && !decodeResult.isEmpty()) {
							/*
							 * YES, we got one message , stop here and exit the
							 * function, since one solution is enough
							 */
							logger.info("decoding data using key " + i
									+ " success!");
							return decodeResult;
						}
					} catch (Exception e) {

						/*
						 * continue if error occurs error is possible because
						 * the key used by server is constantly changing
						 */

						e.printStackTrace();
						logger.info("decoding data using key " + i
								+ " failed, retrying;" + decodeResult);
						continue;
					}
				}

			} else {
				/* sth is wrong with updateKeyList */
				logger.warn("no group key found ");
			}
		} catch (Exception e) {

			logger.warn(e);

		}
		return null;
	}

}
