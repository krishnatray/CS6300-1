package com.team25project3.webapp.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.team25project3.webapp.shared.TaskInfo;
import com.team25project3.webapp.client.SaveTaskService;
/**
 * The server-side implementation of the RPC service.
 */


@SuppressWarnings("serial")
public class SaveTaskServiceImpl extends RemoteServiceServlet implements
	SaveTaskService {


	

	@Override
	public String saveTask(TaskInfo[] name) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
		List<TaskInfo> list = new ArrayList<TaskInfo>(Arrays.asList(name));
		String user = name[0].getUserId(); 
		//persistenceManager pm = PMF.get().getPersistenceManager();       
			
		DatastoreService datastore = DatastoreServiceFactory
		                .getDatastoreService();
		
		@SuppressWarnings("deprecation")
		Query q = new Query("TaskInfo").addFilter("userid",Query.FilterOperator.EQUAL,user);
	    
		PreparedQuery pq = datastore.prepare(q);
		
		
		for (Entity result : pq.asIterable()) {				
				for(int i = 0; i < list.size(); i++){
					
					boolean change;
					long IdTemp; 
					change = false; 
					
					
					
					IdTemp = Long.parseLong(list.get(i).getId()); 
					
				
					if(result.getKey().getName() != null){
						if(result.getKey().getName().equals(list.get(i).getId()))
							change = true;
					}
					
					/*else if(IdTemp == result.getKey().getId())
						change = true; */
					
					if(change == true){
						Date date = new Date(); 
						result.setProperty("name", list.get(i).getName());
						result.setProperty("note", list.get(i).getNote());
						result.setProperty("duetime", list.get(i).getDueTime());
						result.setProperty("priority", list.get(i).getPriority());
						result.setProperty("checked", list.get(i).getChecked());
						result.setProperty("lastupdated", list.get(i).getLastUpdated());
						result.setProperty("noduetime", list.get(i).getNoDueTime());
						datastore.put(result);
						list.remove(i);
						i = list.size() + 1; 
					}
				}

		}
		
		
		for(int i = 0; i < list.size(); i++){
			Date date = new Date();
			Entity temp = new Entity("TaskInfo", list.get(i).getId());
			temp.setProperty("name", list.get(i).getName());
			temp.setProperty("note", list.get(i).getNote());
			temp.setProperty("duetime", list.get(i).getDueTime());
			temp.setProperty("priority", list.get(i).getPriority());
			temp.setProperty("userid", user);
			temp.setProperty("checked",list.get(i).getChecked());
			temp.setProperty("lastupdated",list.get(i).getLastUpdated());
			temp.setProperty("noduetime",list.get(i).getNoDueTime());
			datastore.put(temp);
		}
	
		
	  
		return "Success";
	}
}
