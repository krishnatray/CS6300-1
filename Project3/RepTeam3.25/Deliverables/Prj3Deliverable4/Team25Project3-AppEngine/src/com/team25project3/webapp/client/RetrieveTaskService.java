package com.team25project3.webapp.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.team25project3.webapp.shared.TaskInfo;


@RemoteServiceRelativePath("retrieve")
public interface RetrieveTaskService extends RemoteService {
	TaskInfo[] retrieveTask(String name) throws IllegalArgumentException;
}



