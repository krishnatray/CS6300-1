# **Project 3 – Vision Document –** 

## **1 Introduction**
 
###**1.1 Document Purpose and Scope** 

This document outlines the vision for the **_ToDoList_** **_Manager_** extended-web application. 

The purpose of this document is to: 

- Identify and describe the problems faced by end users and the effects of those problems on productivity and efficiency 
- Gather and describe customer requests for software features 
- Propose a solution  
- Identify any constraints to the proposed solution 
- Identify key stakeholders and users 

The scope of the document is the redesign & architecting of the **_ToDoList_** **_Manager_** Android application to incorporate a web-based application capability. 


## **2 Problem Statement** 

**_The problem of:_**

The limitations imposed by the implementation of the **_ToDoList_** **_Manager_** application on a standalone (non-connected) Android device

**_Affects:_**

The ability of stakeholders to access and maintain information regarding business activities on an effective and efficient basis

**_Creating the impact of:_**

Inefficiencies in managing the activities by business stakeholders due to the standalone nature of the current application as well as the limitations in capturing stakeholder activity information across the organization on a timely and consistent basis  

**_The successful solution would provide:_**

A new application, leveraging the functionality of the existing **_ToDoList_** **_Manager_** application, that provides stakeholders the ability to create and maintain a centralized collection of activities that can be created, updated, and accessed on both Android and web browser based user interfaces


## **3 Product Overview & Perspective**

###**_3.1 Existing ToDoList_** **_Manager Application_** 
The **_ToDoList_** **_Manager_** application is an Android-based tool that is designed to maintain a list of activities for a user. The application is written in Java and operates on a standalone basis. It provides the user with functionality to add, update, and delete tasks through a graphical UI on an Android device. 

###**_3.2 Web-Extended_** **_ToDoList_** **_Manager_** **_Application_**
The web-extended **_ToDoList_** **_Manager_** application builds upon the core functionality of the Android application and extends the same functionality to a web-based application. Both applications will have a similar set of functional capability and process flow. The application will have a persistence layer for each version and there will have the ability to synchronize data between the versions on request. 

## **4 Product Features**

###**_4.1 Access Activities_** **_using_** **_Android &_** **_Web Browser_** **_Interface_**

4.1.1 Application will allow for the access the set of activities from both the handheld & web-based access interfaces under a single user id

4.1.2 Activities maintained on both the Android & web-based applications should be considered as a single persistence layer maintained across the two implementations

4.1.3 Persistence store should have the same schema & attributes across both implementations

4.1.4 Activities stored across the two implementations do not have to be identical at all times, however the application needs the ability to synchronize the activities in the persistence store on an on-demand basis
 
###**_4.2_** **_Update Activities using_** **_Android &_** **_Web Browser Interface_**

4.2.1 Update capabilities in scope encompasses activity creation, change, and delete of activities

4.2.2 Activities can be updated using both the Android and Web applications

4.2.3 Process flow and screen interactions used by the two application platforms for activity updates should be similar

4.2.4 Updated activities should be stored in the local persistence store of the application platform

4.2.5 Activities changed on one application platform will be flagged and transferred to the other application platform during the synchronization process

###**_4.3_** **_Allow for Display of Completed/Non-completed Activities using Android & Web Browser Interface_**

4.3.1 Display of activities can be filtered by completion status (completed/non-completed)

4.3.2 Status of the activity is maintained through the activity update process

4.3.3 Process flow and screen interactions used by the two application platforms for completed status selection and display should be similar

###**_4.4_** **_Create_** **_User ID and Password_** **_for_** **_Android &_** **_Web Application_**

4.4.1 Create a User ID and password for the web application from the initial login screen using a valid email address as the identifier

4.4.2 Verify that the User ID is not already used in the application

4.4.3 Verify that the password is present and has a minimum length 

4.4.4 Encrypt the password as it is stored at the persistence layer

###**_4.5_** **_Login to_** **_Android &_** **_Web Application using User ID and Password_**

4.5.1 Verify the User ID & Password are valid

4.5.2 Invalid login credentials will require appropriate response messages

4.5.3 Login process flow and screen interactions used by the two application platforms for activity updates should be the same

###**_4.6_** **_Synchronize Activities_** **_Initiated from Android Persistence Layer to Web Persistence Layer_**

4.6.1 Synchronization of activities are made between the persistence layers and initiated from the Android application 

4.6.2 Synchronization is two-way: the changes made in each persistence layer are transferred to the other system 

4.6.3 Modifications made to activities at the persistence layer are identified and transferred in the synchronization process

###**_4.7_** **_Synchronize User ID and Passwords Initiated from Android Persistence Layer to Web Persistence Layer_**

4.7.1 Synchronization of user ids & passwords are made between the persistence layers and initiated from the Android application 

4.7.2 Synchronization is two-way: the changes made in each persistence layer are transferred to the other system 

4.7.3 Modifications made to user IDs and passwords at the persistence layer are identified and transferred in the synchronization process

###**_4.8_** **_Encrypt Persistence Layer of Both Android and_** **_Web Application_**

4.8.1 User IDs & passwords should be encrypted at the persistence layer 

4.8.2 Events should be encrypted at the persistence layer 

## **5** **Constraints**
 
###**_5.1 Design Constraints_** 

5.1.1 System-Supported Platforms 
The solution will extend the functionality of the Android **_ToDoList_** **_Manager_** application to a web-based platform. The new application will be developed leveraging standard platform tools and languages used for the architecture of web-based and mobile applications. These include: 

- Java
- Eclipse
- Android 
- Google Web Toolkit
- Google application server platform

The application will allow access from the Android-based **_ToDoList Manager_** application as well as providing access to the application functionality from web browsers running on Windows, Linux, Macintosh, and UNIX standard browsers. 

5.1.2 Expose Features as Services
Where possible, the system will be designed in such a way that its features can be exposed as services. 

5.1.3 Use Existing Services and Data
Where possible, the system will use existing services and data including capabilities provided by the development framework. 

5.1.4 Browser Compatibility
System should allow access from web browsers, including Internet Explorer, Firefox, Safari, and Chrome. 

## **6** **Stakeholder and User Descriptions**

###_**6.1 Stakeholders**_

| **Name**          | **Represents**   | **Project Role**  |
| :---------------- |:-------------- |:------------------|
| Project Sponsor   | Owner          | Funding, Sign Off |
| End Users         | App Target Audience| Functional Requirements      |
| Development       | App Creation   | Technical Resources |
| Support           | Ongoing Support| Technical Support|


###_**6.2 Users**_

| **Title**             | **Role**   | **Description of Use** |
| :----------------  |:----------|:------------------|
| Handheld Users     | Mobile           | Access to App on Android Device  |
| Web Users          | Internet/Intranet| Access to App from Web |
| Combined Access    | Mobile & Internet| Access to App from Both |
















