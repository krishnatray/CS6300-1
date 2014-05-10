package com.team25project3.webapp.shared;

import java.util.Comparator;

public class TaskComparator implements Comparator<TaskInfo>{
	 public int compare(TaskInfo o1, TaskInfo o2) {
	        return o1.getDueTime().compareTo(o2.getDueTime());//o1.getStartDate().compareTo(o2.getStartDate());
	    }
}
