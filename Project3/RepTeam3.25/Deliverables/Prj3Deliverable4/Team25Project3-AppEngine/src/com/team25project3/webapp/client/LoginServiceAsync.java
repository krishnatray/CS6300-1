package com.team25project3.webapp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface LoginServiceAsync {
	void loginUser(String input[], AsyncCallback<String> callback)
			throws IllegalArgumentException;
}
