package com.xxu.rest;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.xxu.client.Client;
import com.xxu.security.RSAClient;
import com.xxu.util.ByteHexConversion;

public class AuthenticationServiceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		try {
			Client c = new Client("/auth/usr/1/");
			c.getReply();
			String decode_result = RSAClient.decryptData(ByteHexConversion
					.HexToBytes(c.getReply()));
			c = new Client("/auth/testPost");
			System.out.println(c.clientPost(decode_result));
			Assert.assertEquals(c.clientPost(decode_result), 200);
		} catch (Exception e) {

			e.printStackTrace();
			Assert.fail();

		}
	}
	@Test
	public void test2() {
		try {
			Client c = new Client("/auth/usr/1/");
			c.getReply();
			String decode_result = RSAClient.decryptData(ByteHexConversion
					.HexToBytes(c.getReply()));
			TimeUnit.SECONDS.sleep(2);
			c = new Client("/auth/testPost");
			System.out.println(c.clientPost(decode_result));
			Assert.assertEquals(c.clientPost(decode_result),403);
		} catch (Exception e) {

			e.printStackTrace();
			Assert.fail();

		}
	}
	
	@Test
	public void test3() {
		try {
			Client c = new Client("/auth/usr/1/");
			c.getReply();
			String decode_result = RSAClient.decryptData(ByteHexConversion
					.HexToBytes(c.getReply()));
			c = new Client("/auth/testPost");
			System.out.println(c.clientPost(""));
			Assert.assertEquals(c.clientPost(""), 203);
		} catch (Exception e) {

			e.printStackTrace();
			Assert.fail();

		}
	}

}
