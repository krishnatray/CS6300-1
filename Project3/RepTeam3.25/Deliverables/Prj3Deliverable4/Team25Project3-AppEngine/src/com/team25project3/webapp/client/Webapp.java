package com.team25project3.webapp.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.dom.client.Document;
import com.team25project3.webapp.shared.FieldVerifier;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Webapp implements EntryPoint {
	
	
	LoginLayout login;
	ToDoLayout todo;
	
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final RegisterServiceAsync registerService = GWT
			.create(RegisterService.class);
	
	public static void setBrowserWindowTitle (String newTitle) {
	    if (Document.get() != null) {
	        Document.get().setTitle (newTitle);
	    }
	}


	/**
	 * This is the entry point method.
	 */
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		login = new LoginLayout(); 
		login.onModuleLoad();
		login.setMaster(this);
		//ToDoLayout todo = new ToDoLayout();
		//todo.onModuleLoad();
	}
	
	public void loadUser(long user){
		todo = new ToDoLayout();
		todo.setUserId(user);
		todo.onModuleLoad();		
		login = null; 
	}
}
