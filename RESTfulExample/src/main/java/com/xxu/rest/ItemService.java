package com.xxu.rest;

import com.xxu.security.RSAServer;
import com.xxu.type.*;
import com.xxu.util.ByteHexConversion;
import com.xxu.database.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

//import database
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/item")
public class ItemService {
	@BadgerFish
	@GET
	@Path("/status/{param}")
	public Item InquiryStatus(@PathParam("param") String ID) {

		Item return_item = ItemDatabase.inquiry(ID);

		return return_item;
	}

	@BadgerFish
	@GET
	@Path("/secure/status/{param}")
	public String InquiryStatus(@HeaderParam("token") String token,@PathParam("param") String ID) throws Exception {
		System.out.println(token+" "+ID);
		if (TokenDatabase.varifyToken(token)) {
			try {
				Item return_item = ItemDatabase.inquiry(ID);
				return ByteHexConversion.BytesToHex(RSAServer.encryptData(
						return_item.toString(), "1"));
			} catch (Exception e) {

				e.printStackTrace();

			}
		} 
			return "";
	}

	public static void main(String args[]) {
		ItemService b = new ItemService();
		Item a = b.InquiryStatus("1");
		a.print();
	}

}
