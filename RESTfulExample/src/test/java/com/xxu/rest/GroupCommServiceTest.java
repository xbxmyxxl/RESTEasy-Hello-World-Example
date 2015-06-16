package com.xxu.rest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.xxu.client.Client;
import com.xxu.client.GroupClient;
import com.xxu.security.RSAClient;
import com.xxu.util.ByteHexConversion;

public class GroupCommServiceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		GroupClient client1;
		GroupClient client2;
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
	public void test() throws Exception {
		
		GroupClient client1 = new GroupClient("1","Private.key");
	}

}
