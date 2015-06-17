package com.xxu.rest;

import com.xxu.util.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.ws.rs.Path;

import com.xxu.database.*;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

import com.xxu.security.*;

import javax.ws.rs.PathParam;

@Path("/auth")
public class AuthenticationService {
	final Logger logger = Logger.getLogger(AuthenticationService.class);

	/**
	 * @param id
	 * @return
	 * @throws IOException
	 * 
	 *             require the encoded token by id of the user, the
	 */
	@GET
	@Path("/usr/{id}")
	public String encodeRandomString(@PathParam("id") String id)
			throws IOException {

		/* generate the random string */
		String random_string = RandomStringGenerator.StringGenerator();
		logger.info("the randomly generated string is" + random_string);

		/* store the random string into database */
		TokenDatabase.insertTokenById(id, random_string);

		/* encrypt the data using the public key retrieved from the database */
		byte[] encryptedData = RSAServer.encryptData(random_string, id);
		logger.info("id is :" + id
				+ ByteHexConversion.BytesToHex(encryptedData));

		return ByteHexConversion.BytesToHex(encryptedData);
	}

	/**
	 * @param token
	 * @return
	 * @throws Exception
	 * 
	 *             authentication using header
	 */
	@GET
	@Path("/testHeader")
	public Response putBasic(@HeaderParam("token") String token)
			throws Exception {

		try {
			logger.info("new client with token: " + token);
			/* check whether user submit any authentication information */
			if (token == null || token.isEmpty()) {
				return Response.status(203)
						.entity(" 203 No Authentication Information").build();
			}

			/* Verify the token */
			if (TokenDatabase.varifyToken(token)) {
				
				/* valid */
				return Response.status(200).entity("200 client authenticated")
						.build();
			} else {
				
				/* invalid */
				return Response.status(403).entity("403 Forbidden").build();
			}

		} catch (Exception e) {
			
			/*error occurs*/
			e.printStackTrace();
			logger.warn(e);
			return Response.status(500)
					.entity("Server was not able to process your request")
					.build();
		}
	}

	/**
	 * @param token
	 * @return
	 * @throws Exception
	 * 
	 *             authentication using post method, please see above for explanations
	 */
	@POST
	@Path("/testPost")
	public Response authenticateUsrToken(String token) throws Exception {
		try {
			logger.info("new client with token: " + token);
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

}
