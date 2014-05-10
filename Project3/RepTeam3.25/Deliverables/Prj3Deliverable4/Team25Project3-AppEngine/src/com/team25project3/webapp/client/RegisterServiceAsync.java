package com.team25project3.webapp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface RegisterServiceAsync {
	void registerUser(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;
}
