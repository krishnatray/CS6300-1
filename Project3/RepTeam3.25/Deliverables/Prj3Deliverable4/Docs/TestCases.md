# Test Plan - Project 3 – Team 25

#1 Introduction

This document describes the software test plan for Team 25 Project 2 of the Spring 2014 Semester of CS-6300.  The project is for the design, development, and testing of To-Do Application for the Android platform that syncs with the Google App Engine and has a comparable Web Application for accessing and modifying the todo information. 

##2 Quality Control

###2.1 Test Plan Quality 
The test plan is deigned to evaluate the functional capabilities of the program. The standards of quality includes software that is reasonably bug-free, meets functional requirements specifications and its maintainable.  The quality testing of the program will be a verification process undertaken to establish that the functionality of the program operates in accordance with the requirements identified in elicitation process.  The testing process design will include aspects of requirement-based, partition, and structural testing elements. 

###2.2 Adequacy Criterion
The testing will consist of a series of tests which align with the functional requirements of the application. Each test will evaluate for both a positive and negative response to analyze the partition and structural capability of the program. In addition, there will be testing of the user interface functionality and a stress test that will randomly enter values into the application to verify application stability and reliability.

###2.3 Bug Tracking
Any defects identified in the testing process will be logged in the GitHub project for correction. GitHub will be used for the management of the life cycle of the programs bugs, including identification, resource allocations, and resolution. 


##3 Test Strategy
###3.1 Testing Process
The testing process for the To-Do Android Application, To-Do Web Application, and syncing to the App Engine will be broken down into different phases:

1) Component Unit Testing

2) System Testing

3) Stress Testing

The component unit testing will be performed by the development team and will evaluate the basic functionality of the application. The system and stress testing will have the objective of evaluating the response of the application to a variety of inputs, both positive and negative.

The system and stress testing will be performed by a separate testing team and the results of the tests recorded in the GitHub website for the application.

The system testing will leverage a set of defined test cases with an expected outcome. The test case definitions will be derived from the functional requirements of the application described in the requirements document. In addition, there will be a set of test cases focused on evaluation of the user interface functionality. The execution of the system test cases for the application will be through the use of a testing tool from the Android development suite.

The stress test of the application will evaluate the response of the entry of a variety of random inputs. The application is expected to correctly handle this type of input. This type of testing will be performed by the Monkey tool provided in the Android development tool kit.

The acceptance and regression testing of the application will consist of the successful execution of both the system and stress test cases. Any errors detected in the formal testing process will be recorded in the bug tracker for the application on the GitHub website.

###3.2 Technology

Unit testing of the To-Do application will be performed by the development team and will use a local set of test data to evaluate the functionality of the application. Once completed, the development team will transfer the application to the testing team for formal testing.

System and stress testing of the application will leverage testing tools that operate with the Android development platform and the Google Web Toolkit(GWT). Due to the Java nature of Android Application and GWT, JUnit will be used to test the various new elements added the To-Do Android application and the Web Application. 

Due to the nature of Syncing between two platforms automated test will not be used here, instead many of these test will be handled manually so that certain controls can be handled and a better eye on be kept on both the syncing process to the Datastore contained on the Google App Engine. 


##4 Test Cases

###4.1 Android Testing

| ID  | Test Area | Scenario | Description | Testing Criteria |
|---|---|---|---|---|
| A1 | Functional | Add User | Add new user and password to application | Verify unique user, verify userid format, verify password, verify userid & password storage |
| A2  | Functional  | Login Sequence  | Enter user credentials and login into application | Verify 	successful login, access and display associated data for account|
| A3  | Functional  | Add Tast | Enter data elements to create a new task | Verify default values, 	verify unique task, verify data elements format |
| A4  | Functional  | Change Task Date  | Modify the date of an existing task | Verify valid date, 	validate application of date update |
| A5 |  Functional | Change Task Priority   | Modify the priority of an existing task | Verify valid 	priority value, validate application of priority update |
| A6  | Functional  | Change Task Description  | Modify the description of an existing task | Verify 	description length, validate application of description update |
| A7  | Functional  | Chnage Task Status  | Modify the status of an existing task | Verify valid 	status value, validate application of status update |
| A8  | Functional  | Delete Task   | Delete an existing task from the application | Select an existing 	task, verify removal of deleted event |
| A9  | Functional  | Display Task Server  | Display the detail of an task stored in the application | 	Select 	specific task, verify date, description, status and priority from the datastore if 	connected to internet |
| A10  | Functional  | Display task Local   | Display the detail of an task stored in the application | 	Select 	specific task, verify date, description, status and priority from the local 	database while internet connection is off |
| A11  | Functional  | Display Task List  | Display list of tasks stored in the application | Verify 	sequence, priority, date, description, status. Verify count of records stored in the 	application |
| A12| Functional  | Default Values  | Verify the default values of the application | Create task 	with default entries. Verify values assigned to task |
| A13  | UI  | Login Sequence  |  Verify screen flow from login screen to initial display screen | Login 	to application, verify initial screen display |
| A14  | UI  | Display List to Detail  | Verify screen flow from from the task list screen to detail 	screen | Login to application, verify detail data, return back to task list screen |



###4.2 Web Application Testing

| ID  | Test Area | Scenario | Description | Testing Criteria |
|---|---|---|---|---|
| W1  | Functional  | Login Sequence | Enter user credentials and login into application | Verify successful login, access and display associated data for account |
| W2  | Functional  | Add Task  | Enter data elements to create a new task | Verify default values, verify unique task, verify data elements format |
| W3  | Functional  | Change Task Date  | Modify the date of an existing task | Verify valid date, validate application of date update |
| W4  | Functional  | Change Task Priority  | Modify the priority of an existing task | Verify valid priority value, validate application of priority update |
| W5  | Functional  | Change Task Description | Modify the description of an existing task | Verify description length, validate application of description update |
| W6  | Functional  | Change Task Status  | Modify the status of an existing task | Verify valid status value, validate application of status update |
| W7  | Functional  | Task List Scroll  | Verify that the task list can scroll | Verify that the task list scrolls when it becomes necessary to display all the task |
| W8  | Functional  | Task List Save  | Verify that the user's task can sync to the datastore| Verify that the task list saves to the datastore by pressing save |
| W9  | UI  | Login Sequence | Verify screen flow from login screen to initial display screen | Login 	to web application, verify initial screen display with the task list on the left hand and the task edit screen on the right |



###4.3 DataStore Testing


| ID  | Test Area | Scenario | Description | Testing Criteria |
|---|---|---|---|---|
| D1 | Functional | Add User | Add new user and password to application  | Verify unique user, verify userid formate, verify password encryption, verify userid and password 	storage on App Engine DataStore  |
| D2 | Functional | Add Task |Adding test to datastore |   Verify	unique user, verify userid formate, verify password encryption, verify userid and password 	storage on App Engine DataStore |
| D3 | Functional | Edit Task | Edit task saved to datastore  | Verify 	unique user, verify userid formate, verify password encryption, verify userid and password 	storage are saved to the App Engine DataStore  |
| D4 | Functional | Login | Verify Login | Verify login on both Android and Web Application pulls all 	necessary information from the AppEngine Datastore |



##5 Traceability to Use cases

Referencing the [Use Case Document](UseCase.md), the following traceability is provided for the Test Cases.

| Use Case Number | Description | Test Case Traceability |
| --------------- | ----------- | ---------------------- |
| 1 | ToDo List App System | A1, A2, A3, A10, W1, W2, W8, D1, D2, D3, D4 |
| 2 | View ToDo Items | A9, A10, A11, W7, W8 |
| 3 | Create ToDo Items | A3, W2 |
| 4 | Edit ToDo Items | A4, A5, A6, A7, W3, W4, W5, W6 |
| 5 | Delete ToDo Items | A8 |
| 6 | Mark ToDo Item Complete | A7 |
| 7 | Sync Items | A9, D4 |
| 8 | Session Login | A13, W1 |


##6 Results

Test were preformed on the following platforms.

| Testing Platform | System Testing |
|------------------|----------------|
| Mac OSX 10.9     | Various Android Emulators |
| Windows 8        | Various Android Emulators |
| Windows 8.1      | Various Android Emulators |
| Motorola Moto G  | Physical Testing on Android Hardware |
| Samsung Galaxy S4  | Physical Testing on Android Hardware | 
| Safari (OSX)     | Testing Web Application |
| Internet Explorer(Windows) | Testing Web Application |
| Chrome (Windows/Mac) | Testing Web Application |
| FireFox (Windows/Mac) | Testing Web Application |

All testing preformed as expected after various bug fixed.  There is one exception that was found during testing which can be attributed to a know bug with the Google AppEngine.  The Bug in question is  [Issue 10017](https://code.google.com/p/googleappengine/issues/detail?id=10017) which pretains to OSX and the Use of endpoints.  Requesting the endpoints will occassionally return an HTTP 404 Error, causing an error with the Android Sync Process. 




