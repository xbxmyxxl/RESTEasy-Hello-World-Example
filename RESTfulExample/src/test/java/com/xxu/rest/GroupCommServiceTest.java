package com.xxu.rest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.xxu.client.Client;
import com.xxu.security.RSAClient;
import com.xxu.util.ByteHexConversion;

public class GroupCommServiceTest {

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
			Client c = new Client("/group/key");
			String codeWords = c.getReply();

			String[] codePerClient = codeWords.split(";");
			String part1 = codePerClient[0];
			String part2 = codePerClient[1];
			String symKey;
			String decode_result;
			for (int i = 0; i <= 1; i++) {
				decode_result = RSAClient.decryptData(
						ByteHexConversion.HexToBytes(codePerClient[i]),
						"Private.key");
				if (decode_result != null) {
					System.out.println("the decofing result is "
							+ decode_result);
				}
			}
			c = new Client("/auth/testPost");
			//System.out.println(c.clientPost(decode_result));
			//Assert.assertEquals(c.clientPost(decode_result), 200);
		} catch (Exception e) {

			e.printStackTrace();
			Assert.fail();

		}
	}

}
