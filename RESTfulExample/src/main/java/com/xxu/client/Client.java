package com.xxu.client;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.math.BigInteger;

import org.apache.http.client.ClientProtocolException;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.xxu.type.Item;
 
public class Client {
	ClientRequest request;
	
	public Client(String url) {
		request = new ClientRequest("http://localhost:8080/RESTfulExample/rest"+url);
	}
	
	public int clientPost(String input) throws Exception {
	 
			request.body( "text/html",input);
	 
			ClientResponse<String> response = request.post(String.class);
			return response.getStatus();
	 
			/*if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
			}*/
	 
			
		}
	 
	public String getReply() {
		try {
			ClientResponse<String> response=request.get(String.class);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			String returnStr="";
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				returnStr += output;
			}	
			return returnStr;
		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		}
		return "error";
	}
 
}