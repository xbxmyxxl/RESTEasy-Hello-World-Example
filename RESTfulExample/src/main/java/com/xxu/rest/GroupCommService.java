package com.xxu.rest;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.xxu.database.TokenDatabase;
import com.xxu.security.RSAServer;
import com.xxu.security.RandomStringGenerator;
import com.xxu.util.ByteHexConversion;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.*;

import com.xxu.security.*;
import com.xxu.util.ByteHexConversion;
@Path("/group")
public class GroupCommService {

	public GroupCommService() {
		// TODO Auto-generated constructor stub
	}

	public static List<String> groupMembers = Arrays.asList("1", "2");
	private List<String> l = new ArrayList<String>();
	

	@GET
	@Path("/key")
	public String testString() throws IOException {
		String result="";
		for (String id : groupMembers) {
			result += ByteHexConversion.BytesToHex(RSAServer.encryptData(SymmetricKey.key ,id));
			result += ";";
		}
		
		return result;
	}

	public static void main(String[] args) throws IOException,
			NoSuchAlgorithmException, NoSuchProviderException {
		 GroupCommService  a = new  GroupCommService ();
		 a.testString();
		 System.out.println(Arrays.toString(a.l.toArray()));
		 for (String id : groupMembers) {
			 
		}
		 for (Iterator<String> iter = groupMembers.iterator(); iter.hasNext(); ) {
	            String number = iter.next();
	            
	        }

	}

}
