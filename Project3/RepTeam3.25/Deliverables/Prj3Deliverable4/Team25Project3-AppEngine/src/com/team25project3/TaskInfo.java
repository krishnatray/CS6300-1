package com.team25project3;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TaskInfo {

@Id
private String id;
private String userid;
private String name;
private String note;
private String duetime;
private int noduetime;
private int checked;
private int priority;
private String lastupdated;


public TaskInfo() {
}

public String getId() {
return id;
}

public String getUserId() {
return userid;
}

public String getName() {
return name;
}

public String getNote() {
return note;
}

public String getDueTime() {
return duetime;
}

public int getNoDueTime() {
return noduetime;
}

public int getChecked() {
return checked;
}

public int getPriority() {
return priority;
}

public String getLastUpdated() {
return lastupdated;
}

public void setId(String idIn) {
this.id = idIn;
}

public void setUserId(String idIn) {
this.userid = idIn;
}

public void setName(String nameIn) {
this.name = nameIn;
}

public void setNote(String noteIn) {
this.note = noteIn;
}

public void setDueTime(String duedateIn) {
this.duetime = duedateIn;
}

public void setNoDueTime (int noduetimeIn) {
	this.noduetime = noduetimeIn;
}

public void setPriority(int priorityIn) {
this.priority = priorityIn;
}

public void setChecked(int completedstatusIn) {
this.checked = completedstatusIn;
}

public void setLastUpdated(String lastupdatedIn) {
this.lastupdated = lastupdatedIn;
}

}

