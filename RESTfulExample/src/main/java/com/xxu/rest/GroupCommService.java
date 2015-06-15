package com.xxu.rest;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.xxu.database.TokenDatabase;
import com.xxu.security.RSAServer;
import com.xxu.security.RandomStringGenerator;
import com.xxu.util.ByteHexConversion;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.*;

@Path("/group")
public class GroupCommService {

	public GroupCommService() {
		// TODO Auto-generated constructor stub
	}

	public List<Integer> groupMembers = Arrays.asList(1, 2);
	ArrayList<Integer> elements = new ArrayList<>();
	elements.add(10);
	elements.add(15);

	@GET
	@Path("/1")
	public String testString() throws IOException {

		System.out.println(Arrays.toString(groupMembers.toArray()));
		groupMembers.add(4);
		System.out.println(Arrays.toString(groupMembers.toArray()));
		return "test " + Arrays.toString(groupMembers.toArray());
	}

	public static void main(String[] args) throws IOException,
			NoSuchAlgorithmException, NoSuchProviderException {
		 GroupCommService  a = new  GroupCommService ();
		 a.testString();

	}

}
