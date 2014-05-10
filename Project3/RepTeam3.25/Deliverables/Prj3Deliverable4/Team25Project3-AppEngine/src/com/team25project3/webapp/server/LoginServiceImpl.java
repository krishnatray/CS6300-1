package com.team25project3.webapp.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.team25project3.webapp.client.LoginService;
//import com.team25project3.webapp.client.RegisterService;
import com.team25project3.webapp.shared.FieldVerifier;
import com.team25project3.webapp.shared.PwdEncrypt;
/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class LoginServiceImpl extends RemoteServiceServlet implements
		LoginService {

	public String loginUser(String input[]) throws IllegalArgumentException {
		// Verify that the input is valid. 
		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input[0] = escapeHtml(input[0]);
		//String password = escapeHtml(input[1]);
		userAgent = escapeHtml(userAgent);
		
		

		String password = PwdEncrypt.encrypt(input[1]);
		//TODO: This is Sample code to demo datastore interaction.
        DatastoreService datastore = DatastoreServiceFactory
                .getDatastoreService();
       
        Query q = new Query("User").addFilter("name",Query.FilterOperator.EQUAL,input[0]);
        q.addFilter("password", Query.FilterOperator.EQUAL, password);
        
        //Filter userNameFilter = new FilterPredicate("name",FilterOperator.EQUAL,input[0]);
        //Filter passWordFilter = new FilterPredicate("password",FilterOperator.EQUAL,password);
        
       //Query q = new Query("User");
        //q.setFilter(userNameFilter);
        //q.setFilter(passWordFilter); 
        
        
        PreparedQuery pq = datastore.prepare(q);
        
        Entity result = pq.asSingleEntity();
       
        if(result == null){
        	return "Failed"; //return "Failed";
        }
        else{
        	//String userId = (String)result.getProperty("id");
        	//int id = (int)result.getProperty("id");
        	return result.getKey().getName(); 
        }
        
		//return "three"; 
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
}
