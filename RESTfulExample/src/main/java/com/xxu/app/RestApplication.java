package com.xxu.app;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.xxu.rest.ItemService;
import com.xxu.rest.AuthenticationService;

public class RestApplication extends Application{
	private Set<Object> singletons = new HashSet<Object>();

	public RestApplication() {
		singletons.add(new ItemService());
		singletons.add(new AuthenticationService());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

}
