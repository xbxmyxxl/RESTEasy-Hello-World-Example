package com.xxu.rest;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.log4j.Logger;

import com.xxu.client.GroupClient;
import com.xxu.database.TokenDatabase;
import com.xxu.security.RSAServer;
import com.xxu.util.ByteHexConversion;
import com.xxu.util.RandomStringGenerator;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.*;

import com.xxu.security.*;
import com.xxu.util.ByteHexConversion;

@Path("/group")
public class GroupCommService {
	final Logger logger = Logger.getLogger(GroupCommService.class);

	public GroupCommService() {
		// TODO Auto-generated constructor stub
	}

	public static List<String> groupMembers = Arrays.asList("1", "2");
	private List<String> l = new ArrayList<String>();

	@GET
	@Path("/{param}")
	public String sendKey(@PathParam("param") String id) throws IOException,
			Exception {
		String result = "";
		System.out.println("keylist from symetric key" + SymmetricKey.keyList);
		List<String> keyList = SymmetricKey.getKeyList();
		for (int i = keyList.size()-1; i >= 0; i--) {

			result += ByteHexConversion.BytesToHex(RSAServer.encryptData(
					keyList.get(i), id));
			if (i != 0)
				result += ";";
			logger.info("sending key " + keyList.get(i));
		}
		// logger.info();
		return result;
	}

	@GET
	@Path("/testdata")
	public String sendEncodedString() throws Exception {
		String testdata = "This is message for group member";
		return ByteHexConversion.BytesToHex(SymmetricKey.encryptData(testdata));
	}

	public static void main(String[] args) throws IOException,
			NoSuchAlgorithmException, NoSuchProviderException, Exception {
		GroupCommService a = new GroupCommService();
		System.out.println("hello" + a.sendKey("1"));
		// System.out.println(Arrays.toString(a.l.toArray()));
		// for (String id : groupMembers) {

		// }
		// for (Iterator<String> iter = groupMembers.iterator(); iter.hasNext();
		// ) {
		// String number = iter.next();

		// }

	}

}
