package com.xxu.rest;

import com.xxu.util.*;

import java.util.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.ws.rs.Path;

import com.xxu.type.*;
import com.xxu.database.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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

import com.xxu.database.UsrDatabase;

@Path("/auth")
public class AuthenticationService {
	@GET
	@Path("/usr/{id}")
	public String encodeRandomString(@PathParam("id") String id)
			throws IOException {

		String random_string = RandomStringGenerator.StringGenerator();
		System.out.println("the randomly generated string is" + random_string);
		TokenDatabase.insertTokenById(id, random_string);

		// encrypt the data
		byte[] encryptedData = RSAServer.encryptData(random_string, id);
		System.out.println(ByteHexConversion.BytesToHex(encryptedData));

		return ByteHexConversion.BytesToHex(encryptedData);
	}

	@GET
	@Path("/testHeader")
	public Response putBasic(@HeaderParam("token") String token)
			throws Exception {

		try {
			System.out.println("new client with token: " + token);
			if (token == null || token.isEmpty()) {
				return Response.status(203)
						.entity(" 203 No Authentication Information").build();
			}

			if (TokenDatabase.varifyToken(token)) {

				return Response.status(200).entity("200 client authenticated")
						.build();
			} else {
				return Response.status(403).entity("403 Forbidden").build();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500)
					.entity("Server was not able to process your request")
					.build();
		}
	}

	@POST
	@Path("/testPost")
	public Response authenticateUsrToken(String token) throws Exception {
		try {
			System.out.println("new client with token: " + token);
			if (token == null || token.isEmpty()) {
				return Response.status(203)
						.entity(" 203 No Authentication Information").build();
			}

			if (TokenDatabase.varifyToken(token)) {

				return Response.status(200).entity("200 client authenticated")
						.build();
			} else {
				return Response.status(403).entity("403 Forbidden").build();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500)
					.entity("Server was not able to process your request")
					.build();
		}

		
	}

	private boolean authenticateUsr(String id, String clientStr) {
		if (TokenDatabase.getTokenById(id) == null)
			return false;
		System.out.println("Expected from user: "
				+ TokenDatabase.getTokenById(id) + "\ngetected from user: "
				+ clientStr);
		return (clientStr.replaceAll("\\s+", "")).equals(TokenDatabase
				.getTokenById(id).replaceAll("\\s+", ""));

	}

	public static void main(String[] args) throws IOException,
			NoSuchAlgorithmException, NoSuchProviderException {
		AuthenticationService s = new AuthenticationService();
		// s.authenticateUsr("1","49ald2mic1b2g1u1ikbj28708d");

	}

}
