package com.xxu.rest;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.ws.rs.Path;

import com.xxu.type.*;
import com.xxu.database.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;



//import java.util.Base64;
import org.apache.commons.codec.binary.Base64;


import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

import com.xxu.security.*;

//import database
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/auth")
public class AuthenticationService {
	
	@GET
	@Path("/usr/1")
	public String InquiryStatus() throws IOException {
		
		String random_string = RandomStringGenerator.StringGenerator();
		System.out.println(random_string);
		
		byte[] encryptedData = RSAServer.encryptData(random_string);
		System.out.println(RSAServer.BytesToHex(encryptedData));
		
		return RSAServer.BytesToHex(encryptedData);
	}
	
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException,NoSuchProviderException{
		AuthenticationService s = new AuthenticationService();
		s.InquiryStatus();
		
	}
	

}
