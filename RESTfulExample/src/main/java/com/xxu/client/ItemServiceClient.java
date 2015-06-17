package com.xxu.client;

import java.io.IOException;
import com.xxu.type.*;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.jboss.resteasy.client.ClientRequest;

/**
 * @author xxu
 *
 *         Client for getting item from ItemDatabase, only difference is it
 *         returns an item in json instead of string
 */
public class ItemServiceClient {

	ClientRequest request;
	final Logger logger = Logger.getLogger(ItemServiceClient.class);

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
	
}
