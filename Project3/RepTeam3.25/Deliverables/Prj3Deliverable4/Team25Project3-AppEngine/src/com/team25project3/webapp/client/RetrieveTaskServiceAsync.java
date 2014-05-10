package com.team25project3.webapp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.team25project3.webapp.shared.TaskInfo;

public interface RetrieveTaskServiceAsync {
	void retrieveTask(String input, AsyncCallback<TaskInfo []> callback)
			throws IllegalArgumentException;
}


