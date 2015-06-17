package com.xxu.rest;

import static org.junit.Assert.*;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.xxu.client.GroupClient;;

public class GroupCommServiceTest {
	
	private static GroupClient client1;
	private static GroupClient client2;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		 client1 = new GroupClient("1", "Private.key");
		 client2 = new GroupClient("2", "Private_2.key");
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
		client1.updateKeyList();
		String outputServer=client1.readResponseFromServer();
		Assert.assertEquals(outputServer,"This is message for group member");
	}
	
	@Test
	public void test2() throws Exception {
		ClientRequest request = new ClientRequest("http://localhost:8080/RESTfulExample/rest"+"/group/remove");
		ClientResponse<String> response=request.get(String.class);
		String outputServer = client1.readResponseFromServer();
		client1.readResponseFromServer();
		System.out.println(outputServer);
		Assert.assertEquals(outputServer,null);
	}
	
	@Test
	public void test3() throws Exception {
		client1.updateKeyList();
		String outputServer = client1.readResponseFromServer();
		client1.readResponseFromServer();
		Assert.assertEquals(outputServer,"This is message for group member");
	}
	
	@Test
	public void test4() throws Exception {
		client2.updateKeyList();
		String outputServer=client2.readResponseFromServer();
		Assert.assertEquals(outputServer,"This is message for group member");
	}


}
