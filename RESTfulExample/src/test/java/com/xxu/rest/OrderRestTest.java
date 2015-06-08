package com.xxu.rest;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.ClientRequest;


public class OrderRestTest {

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
	public void test() throws Exception {
         ClientRequest request = new ClientRequest(
				"http://localhost:8080/RESTfulExample/rest/item/status/2");
		//request.accept("application/json");
        ClientResponse<String> response = request.get(String.class);
        BufferedReader br = new BufferedReader(new InputStreamReader(
				new ByteArrayInputStream(response.getEntity().getBytes())));

		String output;
		String return_string="";
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {

			System.out.println(output);
			return_string += output;
		}
        //String value = ((String) response).readEntity(String.class);
        //response.close();
		
        Assert.assertEquals(return_string.replaceAll("\\s+",""), " Request item number : 2 Information for Item number:2  is : 2: iphone from apple                                             429.0".replaceAll("\\s+",""));
	}

}
