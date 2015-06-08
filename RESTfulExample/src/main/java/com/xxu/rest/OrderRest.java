package com.xxu.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("order")
public class OrderRest {
	@GET
	@Path("/status/{param}")
	public Response InquiryStatus(@PathParam("param") String msg) {

		String header = "Request item number : " + msg + " \r\n";
		//result = ItemDB.inquiry();
		String result = "Information for Item number:" + msg + " : "; 

		return Response.status(200).entity( header + result ).build();
	}
	
	@GET
	@Path("/status/{param}")
	public Response PlaceOrder(@PathParam("param") String msg) {

		String header = "Request item number : " + msg + " \r\n";
		//result = ItemDB.inquiry();
		String result = "Information for Item number:" + msg + " : "; 

		return Response.status(200).entity( header + result ).build();
	}

}
