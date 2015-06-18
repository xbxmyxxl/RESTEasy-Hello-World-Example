package com.xxu.rest;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

import com.xxu.client.Client;
import com.xxu.security.RSAClient;
import com.xxu.util.ByteHexConversion;

public class AuthenticationServiceTest {

	/**
	 * Success because it post to server, immediately after it receives the
	 * token
	 */
	@Test
	public void test() {
		try {
			/* get the authentication information and decode it */
			Client c = new Client("/auth/usr/2/");
			c.getReply();
			String decode_result = RSAClient
					.decryptData(ByteHexConversion.HexToBytes(c.getReply()),
							"Private_2.key");
			System.out.println("the decofing result is " + decode_result);

			/* post the token to the designated url */
			c = new Client("/auth/testPost");
			System.out.println(c.clientPost(decode_result));

			/* should be access ok */
			Assert.assertEquals(c.clientPost(decode_result), 200);
		} catch (Exception e) {

			e.printStackTrace();
			Assert.fail();

		}
	}

	/**
	 * should be forbidden because the token expires
	 */
	@Test
	public void test2() {
		try {
			Client c = new Client("/auth/usr/1/");
			c.getReply();
			String decode_result = RSAClient.decryptData(
					ByteHexConversion.HexToBytes(c.getReply()), "Private.key");

			/* wait for two seconds, while the expiration time is one second */
			TimeUnit.SECONDS.sleep(2);

			c = new Client("/auth/testPost");

			/* token expires so should not be authenticated */
			Assert.assertEquals(c.clientPost(decode_result), 403);
		} catch (Exception e) {

			e.printStackTrace();
			Assert.fail();

		}
	}

	/**
	 * should return 203 because it does not contain any authentication header
	 */
	@Test
	public void test3() {
		try {
			Client c = new Client("/auth/usr/1/");
			c.getReply();
			c = new Client("/auth/testPost");

			/*
			 * post nothing into server, therefore should return authencation
			 * information needed
			 */
			Assert.assertEquals(c.clientPost(""), 203);
		} catch (Exception e) {

			e.printStackTrace();
			Assert.fail();

		}
	}

}
