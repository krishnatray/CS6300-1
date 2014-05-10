package com.team25project3.webapp.shared;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.team25project3.webapp.shared.TaskInfo;

import java.io.Serializable;

public class TaskContainer {
	
	//private classes. 
	private ArrayList<TaskInfo> taskList = new ArrayList<TaskInfo>(); 
	
	public TaskContainer(){
		
	}
	
	public int getCount(){
		return taskList.size();
	}
	
	public TaskInfo getTaskAtPos(int pos){
		return taskList.get(pos);
	}
	
	public void addTask(TaskInfo newTask){
		taskList.add(newTask);
	}
	
	public void clear(){
		taskList.clear();
	}

	public TaskInfo[] getArray(){
		return (TaskInfo[]) taskList.toArray(); 
	}
	
	
	public void sortTask(){
		Collections.sort(taskList, new TaskComparator());
	}
}
