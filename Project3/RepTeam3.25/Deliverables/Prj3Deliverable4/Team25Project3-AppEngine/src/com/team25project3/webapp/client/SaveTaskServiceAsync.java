package com.team25project3.webapp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.team25project3.webapp.shared.TaskInfo;

public interface SaveTaskServiceAsync {
	void saveTask(TaskInfo[] input, AsyncCallback<String> callback)
			throws IllegalArgumentException;
}


