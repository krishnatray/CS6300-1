RISK ASSESSMENT
========

##Introduction
This document outlines the possible risks that are involved with creating the new variant of the MTM android application with syncing capabilities to a web application.  The purpose of this document is to establish how risk could effect the development of the software and what steps could be implemented to minimize the effect of these risks and/or the possibility of these risks occurring. 



##Syncing Introduction
#####About
A known risk in most mobile apps are the known issues with over the air syncing using cloud based databases. Just a quick google search for Evernote Sync issues provides a plethora of issues involving syncing between devices and networks. Syncing has unavoidable issues; history shows that issues will occur but steps can be implemented to lower risk of user issues.   

#####Mitigation Steps 
1. Network connectivity confirmation that informs the user as they open the Mobile App (the MTM) the status of the sync network which in the case will be powered by Google Apps. 
2. Test network failures ahead of time to verify that attempting to sync without network doesn't not effect data on the android application.
3. Attempt to cause network failures during syncing to ensure that this doesn't negatively effect the local database. The Web side also needs to verify so that an incomplete transmission does not cause data corruption.
4. What is the most efficient way to sync data between to devices? After group discussion the current path for sync is to have an automatic sync at the opening and 
closing of the application, along with a manual sync option while in use. 
 
#####Risk
A serious risk is that a syncing issue will occur, causing user data corruption. Sync testing may also present a programmatic risk due to the time required to test and validate the feature.  The team needs to ensure that we allocate an appropriate amount of time to validate syncing so that this does not become a last minute concern. 


##Data Encryption 
#####About
The current version of the MTM android app utilizes data encryption for the user passwords. To maintain this basic level of account security, encrypted transmission and storage of user information is required for the web app. This is a minimum requirement due to the recent concerns concerning digital security.

#####Mitigation Steps
1. Maintain the basic encryption of user password across web communications.  This includes optionally verifying password with the web side from the MTM android.
2. (Optional)Increasing data encryption to cover all users' data during syncing process and storage on Web and MTM.  This would be ideal, but may not be possible with the allotted time for development.

#####Risk
User security is always a concern when it comes to mobile and web apps. We need to evaluate what level of encryption we need to implement in the ToDo app system. 


##Testing
#####About
One of the issues in early development was late testing occurring after much of the app had been developed. With the current development, the goal should be to test concurrently with development. This allows bugs to be discovered early and quickly.

#####Mitigation Steps
1. Estalish test plan script that establishes coverage of the functional requirements of the application.
1. Test early in the development process.
2. Include modular testing of smaller elements of the system.

#####Risk
With out proper testing methods, we risk running into late minute bugs that we might not be able to fix. Early implementation of testing allows for early bug discovery and hopefully fewer last minute issues. 

##Iterative Development
#####About 
Due to the constantly changing nature of iterative design and development, developers and project leads need to be aware of their timeline and goals.  The risk involved with iterative development lie in the occasional lack of complete specifications or changing requirements from iteration to iteration. 

New requirements added; end-users insist on new requirements


#####Mitigation Steps
1. The baseline requirements for the application will need to be established in the inception phase and the changing requirements will be evaluated against the baseline to determine impact on scope and resource requirements.
2. The project lead needs to consistently monitor timeline and tasks, so that when requirements and goals change the project lead can redirect resources appropriately.
3. There needs to be a periodic assessment of requirements and tasks are so that resources are allocated as efficiently as possible. 
4. Project governance structure needs to be established for the review of changing requirements and the approval/disapproval of the new functionality.

#####Risk
With changing goals and requirements it is very easy to place resources into a tasking that may not be fulfilled or possibly thrown out in a later requirements change.  Updates during development are bound to happen, and the goal is to develop in small iterative steps so as to be reactive to changes when they do occur. 
