#Use Case Document: Team 25

## 1. ToDo List App System
### Scope: ToDo List System (MTM and WTM)
### Actor: User
### Level: 1
### Description:
The user authenticates on either the mobile app (MTM) or web app (WTM).  The user is then able to add and edit the list items.  As long as the device is connected to the internet, list items are available across all the user's devices including mobile phone and PC (web browser).  The two user interfaces provide similar functionality so the user is able to seamlessly move between apps.  The overall system is shown graphically below:

![ToDo List System](pics/UseCaseSystemDiagram.png)

## 2. View ToDo Items
### Scope: Mobile or web app
### Actor: User
### Level: 2
### Description:
On either the mobile or web app the user is presented with a scrollable ToDo item list.  The user is able view and sort the list by priority and due date.  Completed items are optionally viewable.

## 3. Create ToDo Items
### Scope: Mobile or web app
### Actor: User
### Level: 2
### Description:
On either the mobile or web app the user is presented with an interface to create ToDo items.  Upon creation the user is presented with fields to add notes, priorities, and due dates, all of which are optional.

## 4. Edit ToDo Items
### Scope: Mobile or web app
### Actor: User
### Level: 2
### Description:
On either the mobile or web app the user is presented with an interface to edit existing ToDo items.  The edit view presents the same options as available during item creation including title, notes, priorities, and due dates.

## 5. Delete ToDo Items
### Scope: Mobile app
### Actor: User
### Level: 2
### Description:
On the mobile app only the user is presented with an interface to delete existing ToDo items.  Since marking a task complete is the normal use case, deletion is confirmed with a dialog box.

## 6. Mark ToDo Item Complete
### Scope: Mobile or web app
### Actor: User
### Level: 2
### Description:
On either the mobile or web app the user is presented with an interface to mark an item as complete.  The user interface is a toggle so an item may be unmarked.  Completed items are optionally viewable.

## 7. Sync Items
### Scope: ToDo List System (MTM and WTM)
### Actor: User
### Level: 2
### Description:
The user authenticates on a mobile phone and all the user's ToDo list items are automatically available whether entered from the web or the phone.  The user can make changes and manually sync them so that they are immediately available on the web.  Alternatively, changes are automatically synced when the user starts the app.

## 8. Session Login
### Scope: Mobile or web app
### Actor: User
### Level: 2
### Description:
Upon app startup (either web or mobile) the user is presented with a login view that permits login as an existing user.  On the Android app, but not the web app, the user is able to create new users. The user is required to login to uniquely track list items.  Once logged, the user remains logged in unless the user explicitly logs out.