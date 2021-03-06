package com.xxu.app;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.xxu.rest.ItemService;
import com.xxu.rest.AuthenticationService;
import com.xxu.rest.GroupCommService;

/**
 * @author xxu
 *
 *         standard application to register restful services
 */
public class RestApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();

	/**
	 * Singleton class registers the rest services to the main application
	 * 
	 * Item : retrieving item info from database . Authentication : authenticate
	 * user using RSA keys exchange. GroupComm : message only visible for group
	 * members, encoded using group key.
	 * 
	 */
	public RestApplication() {
		singletons.add(new ItemService());
		singletons.add(new AuthenticationService());
		singletons.add(new GroupCommService());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

}
