MTM - WTM Lessons Learned
========
##Team 25

##Overview


The objective of the project was to redesign & architect the **_ToDoList_** **_Manager_** Android application to incorporate a web-based application capability. The new MTM -WTM application would be expected to replicate the existing functionality of the Android-based MTM application on the web-based WTM application, as well as providing the ability to synchronize the data stores of the respective applications.

As described in the vision document, the following problem statement frames out the scope of the project:

**_The problem of:_**

The limitations imposed by the implementation of the **_ToDoList_** **_Manager_** application on a standalone (non-connected) Android device

**_Affects:_**

The ability of stakeholders to access and maintain information regarding business activities on an effective and efficient basis

**_Creating the impact of:_**

Inefficiencies in managing the activities by business stakeholders due to the standalone nature of the current application as well as the limitations in capturing stakeholder activity information across the organization on a timely and consistent basis  

**_The successful solution would provide:_**

A new application, leveraging the functionality of the existing **_ToDoList_** **_Manager_** application, that provides stakeholders the ability to create and maintain a centralized collection of activities that can be created, updated, and accessed on both Android and web browser based user interfaces

The project involved the utilization of a number of new technologies and the adaption of an working solution to integrate with a newly developed application across different different platforms. There were a number of lessons learned from the implementation of the project that should be noted for future development work on the application.
  
##Lesson 1 - Learning Curve of New Technology

There was a technical decision at the outset of the project to use the Google AppEngine as the application deployment platform for the WTM application. This framework provides both the application and persistence layers for the application. The Google AppEngine framework is a very sophisticated technical platform and there was a significant learning curve for the team that was associated with getting comfortable with the capabilities provided by the platform. 

In looking at the current design of the first generation of the application, there were some decisions made in the design process that were due to an unfamiliarity with the full functionality of the Google AppEngine. These areas are discussed more in depth in subsequent lessons and could be targeted for optimization in subsequent development work on the application.

##Lesson 2 - Differences Between Relational/Non-Relational Data Stores

One of the key technical decisions in the design was to use the built in data store of the Google App Engine for the persistence layer of the application as opposed to a more traditional relational data base. This decision had some key advantages, particularly since the built-in data store was tightly integrated with the App Engine. However the data store also has specific characteristics that produced some unexpected behaviors.

The non-relational data store architecture of the App Engine does not provide the same update timing as provided by traditional relational data bases. One discovery was that the non-relational datastore index is not updated immediately after changing a task property and, as a result, queries can have inaccurate results while indexing and replication is in progress. This was compensated for by force loading the objects in the endpoints to ensure strong consistency.

The key lesson learned is that there are different characteristics and behaviors associated with different technologies that are not apparent at the outset. Relational-like is not necessarily the exact same thing as relational and there can be unexpected behaviors that can occur with implementations that leverage non-traditional persistence technologies.

##Lesson 3 - Leveraging Different Data Access Methods - RPC vs Rest

The Google App Engine platform is a full featured application framework, providing a range of communication protocols that can be leveraged in an application. The MTM - WTM application architecture uses both the Remote Procedure Calls (RPC)  and web services protocols provided by the App Engine as communication methods to access the persistence layer. 

There are a number of reasons for this approach:

1) The native RPC was believed to be more efficient for use by the web application. This approach was taken because it was felt that there would potentially be less overhead with the RPC than with the web service when accessing the data store on the Google App Engine.

2) The web service was used for the replication function due to the cross-platform communication requirements from the Android application and the Google App Engine. The web service usage simplified the communication architecture since it was supported on both the Android and App Engine platforms.

3) The development of the applications were handled by different members of the project team. The approach that was taken was that each team member used the communication protocol that best fit the requirements of the module they were working on. For the web application the decision was to use RPC and a web service based approach was used for the replication functionality. In addition, there were challenges of the learning curve associated with rapidly developing an understanding of the capabilities of the Google App Engine framework. This also had some influence on the architectural choices that were made for the application. 

There are some advantages and disadvantages with the resulting architecture of the application. On the advantage side, the separation of the functionality means there is no dependency between the components and each one can execute without having to rely on the other. However, this means that there is functionality that is replicated within the application to handle task management. From a maintenance perspective, this results in a more complex environment. While not a major hinderance in terms of functionality, this architecture would potentially be a candidate for refactoring in subsequent releases of the  application.

##Lesson 4 - Challenges of Testing Cross Platform Solutions

There were some challenges that were encountered in the testing process of the application. Some of these were from the unfamiliarity of the technology used in the application as previously noted. Others were due to the cross platform nature of the application, in particular with the replication process between the platforms. Moving from individual unit testing to integration testing was challenging in terms of trying to instrument a testing solution to span across platforms. With the MTM application, one area of concern was to focus on regression testing to insure that the modifications made to implement data replication would not impact the core functionality of the application. 

In addition, besides identifying faults, the complexity of the technology that was used made it difficult to isolate the root cause of problems. In particular, the eventually-consistent nature of the App Engine persistence layer was particularly difficult to isolate. The problems that were encountered manifested themselves in the form of intermittent problems that were difficult to repeat. The App Engine framework is a complicated platform and a lot of the inner functionality of the framework is obfuscated and set through parameters. 

One example of this was encountered in the testing of the synchronization process. Testing of the synchronization functionality was initiated using an Android emulator running on MS Windows connecting to the the persistence store via the endpoints deployed on the Google App Engine. The synchronization worked correctly through the initial testing process. However when the testing was extended to alternative platforms at alternate locations intermittent problems were encountered in which every 5th or 6th synchronization would return a 404. The intermittent nature of the problem made the fault isolation process particularly challenging. Initial indications pointed to an issue with the Android emulator running on Macintosh. However, the problem was able to be replicated at the service API level on multiple platforms, thus was not isolated to a particular platform. In reviewing the open issues recorded for the Google App Engine framework, there are known issues with intermittent errors on App Engine endpoints and the service should not be considered to have guaranteed reliability. The recommendation that was adopted for a workaround was to adapt the calling protocol on the client to repeat the call five times in case of failure. The workaround provided a higher level of consistency of execution across different platforms and compensates for the technical issues inherent in the App Engine platform. 

The key lesson learned was that consideration should be made to consider the risk of the initial use of a development platform and that an understanding of what type of debugging instrumentation is available for the framework. Also, the expectations of the quality of service to be obtained though a service provider may not be up to an expected level and adaptations in the code may be required to compensate for these deficiencies.


##Lesson 5 - Functional Design Process Through Iteration

This is a high level summary of the functional design process that was used to develop the MTM - WTM architecture:

1) Decompose the MTM application to identify architectural components and functionality

2) Instantiate Google App for the web app project

3) Create persistence layer in Google App Engine

4) Design & develop web app on App Engine using RPC for data access

5) Define and instantiate endpoints on App Engine for MTM access to App Engine persistence layer

6) Develop and deploy replication between Android app and Google App Engine persistence layer

As was indicated previously, the development activities were distributed across the team, with team members working on functional components separately, unit testing them, then assembling them for integration testing. This enabled the team to divide up the work to complete development, however the distributed nature of the effort made it difficult to see the end-to-end architecture of the application. The modules were developed independently and then the effort was focused on assembling the components into a whole.

While the implementation was successful, a lesson learned for future developments is that a would be a value in establishing a high level architecture that spans the different platforms at the outset. An emphasis should be placed on identifying and documenting the key integration points between the components and establishing a high level model of the classes before building out the implementation details. 

In this project, the design process was complicated by the aforementioned learning curve associated with the new technology. In future situations where the underlying technology deployed in the architecture was well understood, it would be expected that the functional design would be able to follow a more structured process flow, with more focus on the application functionality and less on the underlying application technology deployment.

## Lesson 6 - Functional Separation of Processing

In looking at the architecture of the MTM - WTM application, it is divided into three major functional components:

1) Android-based MTM application

2) Web-based WTM application

3) Web service interface for the synchronization of data between the MTM & WTM applications

The MTM & WTM applications are independent of each other and only share data through the synchronization process. To some degree this architecture is a function of this specific development exercise, in which an existing application is extended to interoperate with a newly created one. The bulk of the design efforts were on decomposing the functionality of the existing application and translating it to a new platform. In this case the Android application provided the functional framework that the web application was to math and the interface was to allow for the synchronization at the persistence layer. The technology base of the web application was not dependent on the technology base of the Android application and there are clear differences in the architectural compositions of the two versions.

However, another alternative scenario could have been to develop both the Android and Web applications with a common architectural model (for example, to use the same web services in the web application that are used in the transfer process). In this case, this would have required that there would have been more focus on establishing the architecture across all of the platforms. 

The lesson learned from this is that there are different alternative approaches to developing a solution and the decisions made at the outset of development establishes the architectural framework that have implications through to implementation.  In the case of the MTM - WTM application, the key driver was to use the working MTM application as the basis for the new application and to establish a data transfer capability. The architectural decisions that were made were appropriate for the development of a solution that met the requirements as described. However lesson learned is that there should be a planning effort up from to evaluate the potential architectural implications that may arise from the requirements.  


