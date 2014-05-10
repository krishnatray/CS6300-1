package com.team25project3.database;

public class Users {
	  private long id;
	  private String name;
	  private String encryptedPwd;
	  private long lastUpdated;
	  private long lastSync;

	  public long getId() {
	    return id;
	  }

	  public void setId(long id) {
	    this.id = id;
	  }

	  public String getName() {
	    return name;
	  }

	  public void setName(String name) {
	    this.name = name;
	  }
	  
	  public String getPwd() {
		  return encryptedPwd;
	  }
	  
	  public void setPwd(String pwd) {
		  this.encryptedPwd = pwd;
	  }
	  
	  public long getLastUpdated() {
		  return lastUpdated;
	  }
	  
	  public long getLastSync() {
		  return lastSync;
	  }
	  
	  public void setLastUpdated(long lastUpdated) {
		  this.lastUpdated = lastUpdated;
	  }
	  
	  public void setLastSync(long lastSync) {
		  this.lastSync = lastSync;
	  }

	  @Override
	  public String toString() {
	    return name;
	  }
} 
