package com.team25project3.database;

public class ToDoItem {
	  private long id;
	  private long userId;
	  private String name;
	  private String note;
	  private long dueTime;
	  private boolean checked; 
	  private boolean noDueTime;
	  private long priority;
	  private long lastUpdated;

	  public long getId() {
	    return id;
	  }

	  public void setId(long id) {
	    this.id = id;
	  }


	  // Will be used by the ArrayAdapter in the ListView
	  @Override
	  public String toString() {
	    return name;
	  }

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public long getDueTime() {
		return dueTime;
	}
	
	public long getLastUpdated() {
		return lastUpdated;
	}
	
	public void setLastUpdated(long lastUpdated) {
		this.lastUpdated =  lastUpdated;
	}

	public void setDueTime(long dueTime) {
		this.dueTime = dueTime;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isNoDueTime() {
		return noDueTime;
	}

	public void setNoDueTime(boolean noDueTime) {
		this.noDueTime = noDueTime;
	}
	
	public int getNoDueTime() {
		if (noDueTime)
			return 1;
		else
			return 0;
	}
	
	public int getChecked() {
		if (checked)
			return 1;
		else
			return 0;
	}

	public long getPriority() {
		return priority;
	}

	public void setPriority(long priority) {
		this.priority = priority;
	}
} 
