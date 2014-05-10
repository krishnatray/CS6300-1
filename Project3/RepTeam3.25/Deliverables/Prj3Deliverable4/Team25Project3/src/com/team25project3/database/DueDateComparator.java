package com.team25project3.database;

import java.util.Comparator;


public class DueDateComparator implements Comparator<ToDoItem> {

	@Override
	public int compare(ToDoItem arg0, ToDoItem arg1) {
		
		//first due time, then priority;
		if (arg0.isNoDueTime()&&!arg1.isNoDueTime())
			return 1;
		if (arg1.isNoDueTime()&&!arg0.isNoDueTime())
			return -1;
		if ((arg0.isNoDueTime()&&arg1.isNoDueTime())||
		(!arg0.isNoDueTime()&&!arg1.isNoDueTime()&&(arg0.getDueTime()==arg1.getDueTime()))) {
			return (int)(arg1.getPriority()-arg0.getPriority());
		}
		return (int)(arg0.getDueTime()-arg1.getDueTime());
	}

}
