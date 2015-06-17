package com.xxu.client;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

/**
 * @author xxu
 *
 *         client in general, able to connect to url , read response and post
 *         string
 */
public class Client {
	final Logger logger = Logger.getLogger(Client.class);
	ClientRequest request;

	/**
	 * @param url
	 * 
	 *            connect to local host at port 8080, change the hard coded
	 *            prefix here if you are using a different port , on a different
	 *            server
	 */
	public Client(String url) {
		logger.info("connecting to "
				+ "http://localhost:8080/RESTfulExample/rest" + url);
		request = new ClientRequest("http://localhost:8080/RESTfulExample/rest"
				+ url);
	}

	/**
	 * @param input
	 *            the content for the post
	 * @return status code 200,500,403
	 * @throws Exception
	 * 
	 *             post method , post a text string "input" to given url
	 */
	public int clientPost(String input) throws Exception {
		logger.info("posting " + input + " to server");
		request.body("text/html", input);

		ClientResponse<String> response = request.post(String.class);
		return response.getStatus();

	}

	/**
	 * @return the reply from the server, null if error happens
	 */
	public String getReply() {
		try {
			/*
			 * define the response should receive String, change to usr defined
			 * type (Item) if needed
			 */
			ClientResponse<String> response = request.get(String.class);

			/* buffer reader read the response line by line */
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			String returnStr = "";

			while ((output = br.readLine()) != null) {
				System.out.println(output);
				returnStr += output;
			}
			logger.info("Output from Server: " + returnStr);
			return returnStr;
		} catch (ClientProtocolException e) {

			e.printStackTrace();
			logger.warn(e);

		} catch (IOException e) {

			e.printStackTrace();
			logger.warn(e);

		} catch (Exception e) {

			e.printStackTrace();
			logger.warn(e);

		}
		return null;
	}

}