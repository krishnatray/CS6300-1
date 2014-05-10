package com.team25project3.webapp.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.team25project3.webapp.shared.TaskInfo;
import com.team25project3.webapp.client.RetrieveTaskService;
/**
 * The server-side implementation of the RPC service.
 */


@SuppressWarnings("serial")
public class RetrieveTaskServiceImpl extends RemoteServiceServlet implements
	RetrieveTaskService {


	@Override
	public TaskInfo[] retrieveTask(String name)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
		 DatastoreService datastore = DatastoreServiceFactory
	                .getDatastoreService();
	       
	        @SuppressWarnings("deprecation")
			Query q = new Query("TaskInfo").addFilter("userid",Query.FilterOperator.EQUAL,name);
	        
	        PreparedQuery pq = datastore.prepare(q);

	        TaskInfo []res = new TaskInfo[pq.countEntities(FetchOptions.Builder.withDefaults())];
	        int i = 0;
	        
	        for (Entity result : pq.asIterable()) {
	        	String name1 = (String) result.getProperty("name");
	        	String note = (String) result.getProperty("note");
	        	int priority = (int) (long) result.getProperty("priority");
	        	String duetime = (String) result.getProperty("duetime");
	        	int checked = (int) (long) result.getProperty("checked");
	        	String lastup = (String) result.getProperty("lastupdated");
	        	String userId = result.getKey().getName();
	        	int noDue = (int) (long)result.getProperty("noduetime");
	        	if(userId == null)
	        		userId = String.valueOf(result.getKey().getId());
	        
	        	res[i] = new TaskInfo();
	        	res[i].setName(name1);
	        	res[i].setNote(note);
	        	res[i].setPriority(priority);
	        	res[i].setDueTime(duetime);
	        	res[i].setId(userId);
	        	res[i].setChecked(checked);
	        	res[i].setLastUpdated(lastup);
	        	res[i].setNoDueTime(noDue);
	        	i++;
	        	}
	        

		return res;
	}
}
