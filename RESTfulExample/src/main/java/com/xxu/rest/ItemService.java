package com.xxu.rest;

import com.xxu.security.RSAServer;
import com.xxu.type.*;
import com.xxu.util.ByteHexConversion;
import com.xxu.database.*;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;

import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;


//import database
import javax.ws.rs.PathParam;

/**
 * @author xxu
 *
 *         simple rest services for getting inforamtion about the item stored in
 *         the database
 */
@Path("/item")
public class ItemService {
	final Logger logger = Logger.getLogger(ItemService.class);
	/**
	 * @param ID
	 * @return item.toString(), can return the json format id needed
	 */
	@BadgerFish
	@GET
	@Path("/status/{param}")
	public String InquiryStatus(@PathParam("param") String ID) {

		Item return_item = ItemDatabase.inquiryItemById(ID);
		return return_item.toString();
	}

	/**
	 * @param token
	 * @param ID
	 * @return
	 * @throws Exception
	 * 
	 *             method for returning data about item , but user must be
	 *             authenticated in order to do access to the service
	 */
	@BadgerFish
	@GET
	@Path("/secure/status/{param}")
	public String InquiryStatus(@HeaderParam("token") String token,
			@PathParam("param") String ID) throws Exception {
		logger.info(token + " " + ID);
		/*varify the token to authenticate the client, in order to get access to item*/
		if (TokenDatabase.varifyToken(token)) {
			try {
				Item return_item = ItemDatabase.inquiryItemById(ID);
				return ByteHexConversion.BytesToHex(RSAServer.encryptData(
						return_item.toString(), "1"));
			} catch (Exception e) {

				e.printStackTrace();

			}
		}
		return "";
	}


}
