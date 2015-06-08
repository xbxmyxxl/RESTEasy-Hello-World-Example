package com.xxu.app;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.xxu.rest.ItemRest;

public class RestApplication extends Application{
	private Set<Object> singletons = new HashSet<Object>();

	public RestApplication() {
		singletons.add(new ItemRest());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

}
