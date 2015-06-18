package com.xxu.rest;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;

import java.io.InputStreamReader;
import org.junit.Test;
import org.junit.Assert;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.ClientRequest;

public class ItemServiceTest {

	/**
	 * @throws Exception
	 * 
	 *             test what the item retrieved form database, change the
	 *             content of assert if you are using different value in
	 *             database
	 */
	@Test
	public void test() throws Exception {
		ClientRequest request = new ClientRequest(
				"http://localhost:8080/RESTfulExample/rest/item/status/1");
		request.accept("application/json");
		ClientResponse<String> response = request.get(String.class);
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new ByteArrayInputStream(response.getEntity().getBytes())));

		String output;
		String return_string = "";
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {

			System.out.println(output);
			return_string += output;
		}

		Assert.assertEquals(
				return_string.replaceAll("\\s+", ""),
				" Request item number : 2 Information for Item number:2  is : 2: iphone from apple                                             429.0"
						.replaceAll("\\s+", ""));
	}

}
