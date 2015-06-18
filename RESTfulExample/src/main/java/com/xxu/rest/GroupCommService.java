package com.xxu.rest;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.log4j.Logger;

import com.xxu.security.RSAServer;
import com.xxu.security.SymmetricKey;
import com.xxu.util.ByteHexConversion;

import java.util.*;


/**
 * @author xxu
 *
 *         generate group key and send to each usr encoded by their own public
 *         key
 */
@Path("/group")
public class GroupCommService {
	final Logger logger = Logger.getLogger(GroupCommService.class);


	/**
	 * @param id
	 * @return
	 * @throws IOException
	 * @throws Exception
	 * 
	 *             send the group keys(all the currently used valid keys ) to
	 *             user encoded by their public key
	 */
	@GET
	@Path("/{param}")
	public String sendKey(@PathParam("param") String id) throws IOException,
			Exception {
		String result = "";

		/* check if the parameter contains only number */
		if (!id.matches("[0-9]+"))
			return null;

		logger.info("keylist from symetric key" + SymmetricKey.keyList);

		/* get the currently using keys */
		List<String> keyList = SymmetricKey.getKeyList();

		/* iterate through the keys and encode them using client's public key */
		for (int i = keyList.size() - 1; i >= 0; i--) {

			/* encrypt the key */
			result += ByteHexConversion.BytesToHex(RSAServer.encryptData(
					keyList.get(i), id));

			/* this is just formatting */
			if (i != 0)
				result += ";";

			logger.info("sending key " + i + "/" + keyList.size() + " :"
					+ keyList.get(i));

		}
		return result;
	}

	/**
	 * @return
	 * @throws Exception
	 *             testing data
	 * 
	 *             always return the string"this is group message"
	 *             encoded using the symmetric key
	 */
	@GET
	@Path("/testdata")
	public String sendEncodedString() throws Exception {
		String testdata = "This is message for group member";
		return ByteHexConversion.BytesToHex(SymmetricKey.encryptData(testdata));
	}

	/**
	 * @throws Exception
	 *             whenever a group member is removed always generate a new key
	 *             and using it immediately
	 */
	@GET
	@Path("/remove")
	public void removeGroupMember() throws Exception {
		SymmetricKey.removeGroupMember();
	}


}
