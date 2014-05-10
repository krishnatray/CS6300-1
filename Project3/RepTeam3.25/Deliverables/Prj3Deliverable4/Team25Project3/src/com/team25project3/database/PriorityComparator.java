package com.team25project3.database;

import java.util.Comparator;


public class PriorityComparator implements Comparator<ToDoItem> {

	@Override
	public int compare(ToDoItem arg0, ToDoItem arg1) {
		//first priority, then duetime;
		if (arg1.getPriority()==arg0.getPriority()) {
			if (arg0.isNoDueTime())
				return 1;
			if (arg1.isNoDueTime())
				return -1;
			return (int)(arg0.getDueTime()-arg1.getDueTime());
		}
		//inverse sorting
		return (int)(arg1.getPriority()-arg0.getPriority());
	}

}
