package com.xxu.client;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.math.BigInteger;

import com.xxu.database.ItemDatabase;
import com.xxu.type.*;

import org.apache.http.client.ClientProtocolException;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

public class ItemServiceClient {
	ClientRequest request;

	public ItemServiceClient(String ID) {
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
			response.print();
		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		}
	}
	
	public static void main(String args[]) {
		ItemServiceClient a = new ItemServiceClient("1");
		a.GetItem();
	 }

}
