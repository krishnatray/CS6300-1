package com.team25project3.webapp.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.team25project3.webapp.shared.TaskInfo;


@RemoteServiceRelativePath("save")
public interface SaveTaskService extends RemoteService {
	String saveTask(TaskInfo[] name) throws IllegalArgumentException;
}



