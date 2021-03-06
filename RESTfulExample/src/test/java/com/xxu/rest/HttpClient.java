package com.xxu.rest;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.xxu.client.Client;
import com.xxu.security.RSAClient;
import com.xxu.util.ByteHexConversion;

/**
 * @author xxu
 *
 *         a http client to authenticate user based on hearder param
 */
public class HttpClient {

	public static void main(String[] args) {
		try {
			/* get the encoded token from server */
			Client c = new Client("/auth/usr/1/");

			/* decode it */
			String decode_result = RSAClient.decryptData(
					ByteHexConversion.HexToBytes(c.getReply()), "Private.key");

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet getRequest = new HttpGet(
					"http://localhost:8080/RESTfulExample/rest/item/secure/status/1");

			/* add the token to the header */
			getRequest.addHeader("token", decode_result.replaceAll("\\s+", ""));

			/* execute the request */
			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}

			/* read the output */
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));

			String output;
			String resource = "";
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				resource += output;
			}
			System.out.println(RSAClient.decryptData(
					ByteHexConversion.HexToBytes(resource), "Private.key"));
			httpClient.getConnectionManager().shutdown();

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
