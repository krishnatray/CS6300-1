# Process Plan Document – Team 25

## 1 Introduction

This document describes the software development process for Team 25 Project 3 of the Spring 2014 Semester of CS-6300.  The project is titled _TODO List Manager for Android and Web_ and the product to be developed is a refined To-Do app for Android along with a new web interface that syncs to the mobile app.  The goals include:

* Extend the Android application for managing TODO lists.
* Get experience with real-world program comprehension (of undocumented, possibly buggy code)
* Get experience with USP and various new technologies.

### 1.1 Definitions and Acronyms

* LDB: Local Database
* CDB: Centralized Database
* USP: Unified Software Development Process
* MTM: Mobile TODO List Manager
* WTM: Web TODO List Manager

## 2 Process Description

### 2.1 Project Lifecycle
The team will utilize the USP, with phases aligned with the deliverables:

* __Deliverable 1 - Inception__ - Due 11:55pm PST, Sunday, 3/30/14
    * Vision document
	* Initial use-case model
	* Initial risk assessment
	* Initial requirements document
	* Project plan, showing phases and iterations
	* One or more prototypes (optional)
* __Deliverable 2 – Elaboration__ - Due 11:55pm PST, Sunday, 4/13/14
    * Almost complete use-case model
	* Supplementary requirements
	* Software architecture
	* Design model (partially completed)
	* Test cases
	* Executable prototype
	* Revised risk assessment
	* Revised project plan
	* Preliminary user manual
* __Deliverable 3 – Construction__ - Due 11:55pm PST, Sunday, 4/27/14
    * Traceability information for a couple of use cases
	* Software product
	* Completed system tests results
	* User manual
* __Deliverable 4 – Transition__ - Due 11:55pm PST, Sunday, 5/4/14
    * Project completed (all artifacts should be present)
	* Lessons learnt (post-mortem assessment)
* __Individual Assessment__ - Due 11:55pm PST, Tuesday, 5/7/14

Each document and component will have multiple review cycles which will permit revision and refactoring as the design and implementation become more detailed.

### Review Process
All documents and components developed in the project will be maintained in GitHub. The final versions of the project deliverables will be stored in the master branch and and working branches will be established for each of the USP phases. Also, GitHub will be used for defect and remedition tracking. 

The team shall use pull requests to initiate code/document reviews for all deliverables.  The general branch process is as follows:

master -> DxWorking -> DxCode-or-Document-name

for example the working branch of this document would be:

master -> D1Working -> D1ProcessPlan

## 3 Roles

### 3.1 Team members

* Clay Flannigan
* Krishna Nadimetia
* Rich Powers
* Miles Shipman

### 3.2 Role Descriptions and Assignments

| Role | Responsibility | Assignment |
| ---- | -------------- | ---------- |
| Team Leader | The team leader is responsible for coordinating the schedule and planning for the project.  In addition, the project manager is responsible for drafting the Process Plan and Process Assessment Document. The Team Leader will coordinate the repository management and deliverable submittal. | Flannigan |
| Architect | The architect is responsible for developing the software component design and creating high level documentation for critical interfaces.  The architect will have oversight on the developers' products and will perform peer reviews. | Nadimetia |
| Android Developer | The Android Designer is responsible for the low level design and implementation of the native Android app.  Since an initial version of the code is available, the Developer shall document the code via the design documentation.  For new code the Developer shall follow the high level design provided by the Architect. | Flannigan |
| Web Designer | The web designer is responsible for front-end design of the web app including UI and interfaces to the server backend.  The Designer/Developer shall follow the high level design provided by the Architect.  Google Web Toolkit will be the primary development framework.  | Shipman |
| Back End Developer | The back end developer is responsible for designing and implementing the database components, sync and access API for the CDB.  The Designer/Developer shall follow the high level design provided by the Architect. Google App Engine will the primary framework. | Powers |
| Test Engineer | The test engineer is responsible for creating the test plan, executing the tests, documenting the results and working with the developers and architect to address deficiencies. | Shipman |


## 4 Tasks and Estimates

### Task Assignments and Time Estimates

| Phase | Task | Owner | Estimated Hours |
| ----- | ---- | ----- | --------------- |
| D1 - Elicitation | Vision document | Powers | 4 |
| D1 - Elicitation | Initial use-case model | Flannigan | 4 |
| D1 - Elicitation | Initial risk assessment | Shipman | 4 |
| D1 - Elicitation | Initial architecture |  Nadimetia | 8 |
| D1 - Elicitation | Project plan | Flannigan | 4 |
| D1 - Elicitation | One or more prototypes (optional) | Shipman | 6 |
| D2 - Elaboration | Almost complete use-case model | Flannigan | 4 |
| D2 - Elaboration | Supplementary requirements (in vision doc) | Powers | 4 |
| D2 - Elaboration | Software architecture | Nadimetia | 4 |
| D2 - Elaboration | Design model (partially completed) | Nadimetia | 16 |
| D2 - Elaboration | Test cases | Shipman | 6 |
| D2 - Elaboration | Executable prototype | Shipman, Flannigan, Powers | 48 |
| D2 - Elaboration | Revised risk assessment | Shipman | 2 |
| D2 - Elaboration | Revised project plan | Flannigan | 2 |
| D2 - Elaboration | Preliminary user manual | Powers | 2 |
| D3 – Construction | Traceability for use cases | Flannigan | 4 |
| D3 – Construction | Software product | all | 64 |
| D3 – Construction | Completed system tests results | Shipman | 8 |
| D3 – Construction | User manual | Powers | 2 |
| D4 - Transition | Project completed | all | 32 |
| D4 - Transition | Lessons learnt | all | 4 |

### Total Estimated Effort

| Team Member | Total Effort |
| ----------- | ------------ |
| Flannigan | 57 |
| Nadimetia | 53 |
| Powers | 53 |
| Shipman | 67 |
| __Total__ | __230__ |

__Estimated Lines of code:__ 2,500  
__Estimated Defects:__ 40  

### 4.2 Schedule
See deliverable due dates in Section 2.1.

