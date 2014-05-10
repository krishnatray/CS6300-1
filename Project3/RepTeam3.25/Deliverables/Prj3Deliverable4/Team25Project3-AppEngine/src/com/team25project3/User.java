package com.team25project3;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

@Id
private String id;
private String name;
private String password;
private String lastupdated;

public User() {
}

public String getId() {
return id;
}

public String getName() {
return name;
}

public String getPassword() {
return password;
}

public String getLastUpdated() {
return lastupdated;
}

public void setId(String idIn) {
this.id = idIn;
}


public void setName(String name) {
this.name = name;
}

public void setPassword(String password) {
this.password = password;
}

public void setLastUpdated(String updated) {
this.lastupdated = updated;
}
}

