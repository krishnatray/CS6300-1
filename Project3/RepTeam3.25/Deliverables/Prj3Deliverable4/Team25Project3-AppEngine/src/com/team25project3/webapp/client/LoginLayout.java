package com.team25project3.webapp.client;


import java.security.acl.Owner;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style.Display;
import com.team25project3.User;
import com.team25project3.webapp.shared.FieldVerifier;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class LoginLayout implements EntryPoint {
	
	Webapp master; 
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
	
	private final LoginServiceAsync loginService = GWT
			.create(LoginService.class);
	
	public void setMaster(Webapp app){
		master = app; 
	}

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final Button loginButton = new Button("Login");
		//final Button registerButton = new Button("Register");
		final TextBox userNameField = new TextBox();
		final TextBox passWordField = new PasswordTextBox();
		//userNameField.setText("GWT User");
		final Label errorLabel = new Label();


		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("userNameContainer").add(userNameField);
		RootPanel.get("passWordContainer").add(passWordField);
		RootPanel.get("loginContainer").add(loginButton);
		//RootPanel.get("registerContainer").add(registerButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		// Focus the cursor on the name field when the app loads
		userNameField.setFocus(true);
		userNameField.selectAll();

	
		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		//dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		//dialogVPanel.add(textToServerLabel);
		//dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);
		

		//DOM.getElementById("login").getStyle().setDisplay(Display.NONE);
		//image.setVisibleRect(70, 0, 47, 110);
		
		
		


		
		
		
		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				loginButton.setEnabled(true);
				//registerButton.setEnabled(true);
				loginButton.setFocus(true);
			}
		});
		

		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				sendNameToServer();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}
			
			
			/**
			 * Send the name from the nameField to the server and wait for a response.
			 */
			private void sendNameToServer() {
				// First, we validate the input.
				errorLabel.setText("");
				String textToServer = userNameField.getText();
				String passToServer; 
				passToServer = passWordField.getText().toString().trim();; 
				//String passToServer = passWordField.getText();
				//if (!FieldVerifier.isValidName(textToServer)) {
				//	errorLabel.setText("Please enter at least four characters");
				//	errorLabel.setStyleName("gwt-center");
				//	return;
				//}
				
				// Then, we send the input to the server.
				loginButton.setEnabled(false);
				textToServerLabel.setText(textToServer);
				serverResponseLabel.setText("");
				
				String input[] = {textToServer, passToServer}; 
				
				
				loginService.loginUser(input,
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								// Show the RPC error message to the user
								dialogBox
										.setText("Remote Procedure Call - Failure");
								serverResponseLabel
										.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(SERVER_ERROR);
								dialogBox.center();
								closeButton.setFocus(true);
							}

							public void onSuccess(String result) {
								dialogBox.setText("Login Error");
								serverResponseLabel
										.removeStyleName("serverResponseLabelError");
								if(result.equals("Failed")){
									serverResponseLabel.setHTML("Incorrect Password/Username Combination");
									dialogBox.center();
									closeButton.setFocus(true);
								}
								else{
									/*serverResponseLabel.setHTML("Success Logging in ");
									dialogBox.center();
									closeButton.setFocus(true);*/
									DOM.getElementById("login").getStyle().setDisplay(Display.NONE);
									long temp = Long.parseLong(result);
									master.loadUser(temp);//temp
								}
							}
						});
				
			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		loginButton.addClickHandler(handler);
		//registerButton.addClickHandler(handler);
		userNameField.addKeyUpHandler(handler);
		passWordField.addKeyUpHandler(handler);
		//Center Login Button and Register Button
		loginButton.addStyleName("gwt-center");
		//registerButton.addStyleName("gwt-center");
		
	}
}
