package com.xxu.client;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.xxu.database.ItemDB;
import com.xxu.type.*;

import org.apache.http.client.ClientProtocolException;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

public class ItemClient {
	ClientRequest request;

	public ItemClient(String ID) {
		try {

			request = new ClientRequest(
					"http://localhost:8080/RESTfulExample/rest/item/status/"
							+ ID + "/");
			request.accept("application/json");
			
		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	public void GetItem() {
		try {
			Item response = request.get(Item.class).getEntity();

			/*if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}*/
			response.print();
			/*
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}*/
		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		}
	}
	
	public static void main(String args[]) {
		ItemClient a = new ItemClient("1");
		a.GetItem();
	 }

}