package com.team25project3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;
import com.team25project3.database.ToDoItem;
import com.team25project3.database.ToDoItemDataSource;
import com.team25project3.database.UserDataSource;
import com.team25project3.database.Users;
import com.team25project3.taskinfoendpoint.Taskinfoendpoint;
import com.team25project3.taskinfoendpoint.model.CollectionResponseTaskInfo;
import com.team25project3.taskinfoendpoint.model.TaskInfo;

public class SyncTasks extends AsyncTask<Void, Integer, Long> {

	private Activity activity = null;
	UserDataSource userData = null;

	public SyncTasks(Activity callingActivity) {
		activity = callingActivity;
	}

	@Override
	protected Long doInBackground(Void... voids) {

		Taskinfoendpoint.Builder endpointBuilder1 = new Taskinfoendpoint.Builder(
				AndroidHttp.newCompatibleTransport(),
				new JacksonFactory(),
				new HttpRequestInitializer() {
					@Override
					public void initialize(HttpRequest httpRequest) {
					}
				});	

		int timeout;
		Taskinfoendpoint taskEndpoint = CloudEndpointUtils.updateBuilder(
				endpointBuilder1).build();

		ToDoItemDataSource toDoItemData = new ToDoItemDataSource(activity);
		toDoItemData.open();

		userData = new UserDataSource(activity);
		userData.open();

		List <Users> userList = userData.getAllUsers();

		for (int i = 0; i < userList.size(); i++) {
			long userId = userList.get(i).getId();
			long userLastSync = userList.get(i).getLastSync();
			CollectionResponseTaskInfo remoteTaskListInfo = null;

			List <ToDoItem> localTaskList = toDoItemData.getToDoListBySync(userId);


			//

			//Post the record
			timeout = 200;
			for (int inx = 0; inx < 10; inx++)
			{
				try {
					remoteTaskListInfo = taskEndpoint.listTaskInfoSyncUser(String.valueOf(userLastSync), String.valueOf(userId)).execute();
					break;
				} catch (IOException e) {
					if (inx == 9)
						return Long.valueOf(1);
					else
					{
						try {
							Thread.sleep(timeout);
							timeout = timeout+timeout;
						} catch (InterruptedException e1) {
						}
					}
				}
			}
			//




			List<TaskInfo> remoteTaskList = remoteTaskListInfo.getItems();

			// Check to see if any of the remote tasks were also changed locally.
			// If so, sync the newest one
			List <Long> doNotPullTask =  new ArrayList<Long>();
			List <Long> doNotPushTask =  new ArrayList<Long>();
			List <Long> deleteList =  new ArrayList<Long>();
			if(remoteTaskList != null) {
				for (int j = 0; j < remoteTaskList.size(); j++) {
					boolean found = false;
					for (int k = 0; k < localTaskList.size(); k++) {
						if (Long.parseLong(remoteTaskList.get(j).getId()) == localTaskList.get(k).getId()) {
							found = true;
							if (Long.parseLong(remoteTaskList.get(j).getLastUpdated()) <= localTaskList.get(k).getLastUpdated()) {
								doNotPullTask.add(localTaskList.get(k).getId());
							}
							else {
								doNotPushTask.add(localTaskList.get(k).getId());
							}
						}
					}
					if (found == false)
					{
						if (Long.parseLong(remoteTaskList.get(j).getLastUpdated()) <= userLastSync) {
							if (toDoItemData.getItemByItemId(Long.parseLong(remoteTaskList.get(j).getId())) == null)
								deleteList.add(Long.parseLong(remoteTaskList.get(j).getId()));
							else
								doNotPullTask.add(Long.parseLong(remoteTaskList.get(j).getId()));
						}
					}
				}
			}

			//push local items
			for (int j = 0; j < localTaskList.size(); j++) {
				ToDoItem T = localTaskList.get(j);
				if (!doNotPushTask.contains(T.getId())) {		
					TaskInfo tinfo = new TaskInfo();

					tinfo.setId(String.valueOf(T.getId()));
					tinfo.setUserId(String.valueOf(T.getUserId()));
					tinfo.setName(T.getName());
					tinfo.setNote(T.getNote());
					tinfo.setPriority((int)T.getPriority());
					tinfo.setDueTime(String.valueOf(T.getDueTime()));
					tinfo.setNoDueTime(T.getNoDueTime());
					tinfo.setChecked(T.getChecked());
					tinfo.setLastUpdated(String.valueOf(T.getLastUpdated()));


					TaskInfo task = null;

					//
					//Post the record
					timeout = 200;
					for (int inx = 0; inx < 10; inx++)
					{
						try {
							task = taskEndpoint.getTaskInfo(String.valueOf(T.getId())).execute();
							break;
						} catch (IOException e) {
							if (inx == 9)
								return Long.valueOf(1);
							else
							{
								try {
									Thread.sleep(timeout);
									timeout = timeout+timeout;
								} catch (InterruptedException e1) {
								}
							}
						}
					}
					//

					//			

					if (task == null) {	// task not in remote data

						//
						timeout = 200;
						for (int inx = 0; inx < 10; inx++)
						{
							try {
								taskEndpoint.insertTaskInfo(tinfo).execute();
								break;
							} catch (IOException e) {
								if (inx == 9)
									return Long.valueOf(1);
								else
								{
									try {
										Thread.sleep(timeout);
										timeout = timeout+timeout;
									} catch (InterruptedException e1) {
									}
								}
							}
						}


						//
					}
					else {	// existing task

						//

						timeout = 200;
						for (int inx = 0; inx < 10; inx++)
						{
							try {
								taskEndpoint.updateTaskInfo(tinfo).execute();
								break;
							} catch (IOException e) {
								if (inx == 9)
									return Long.valueOf(1);
								else
								{
									try {
										Thread.sleep(timeout);
										timeout = timeout+timeout;
									} catch (InterruptedException e1) {
									}
								}
							}
						}			
					}
				}
			}

			//pull remote items
			if(remoteTaskList != null) {
				for (int j = 0; j < remoteTaskList.size(); j++) {
					TaskInfo remoteTask = remoteTaskList.get(j);
					if (!doNotPullTask.contains(Long.parseLong(remoteTask.getId())) && !deleteList.contains(Long.parseLong(remoteTask.getId()))) {
						ToDoItem toDo = toDoItemData.getItemByItemId(Long.parseLong(remoteTask.getId()));
						if(toDo == null) {	// this is a new task
							toDoItemData.createItemCopy(Long.parseLong(remoteTask.getId()),
									Long.parseLong(remoteTask.getUserId()), 
									remoteTask.getName(), 
									remoteTask.getNote(), 
									Long.parseLong(remoteTask.getDueTime()),
									remoteTask.getNoDueTime() == 1, 
									remoteTask.getChecked() == 1, 
									remoteTask.getPriority(), Long.parseLong(remoteTask.getLastUpdated()));
						}
						else {	// existing task
							ToDoItem remoteToDo = new ToDoItem();
							remoteToDo.setChecked(remoteTask.getChecked() == 1);
							remoteToDo.setDueTime(Long.parseLong(remoteTask.getDueTime()));
							remoteToDo.setId(Long.parseLong(remoteTask.getId()));
							remoteToDo.setName(remoteTask.getName());
							remoteToDo.setNoDueTime(remoteTask.getNoDueTime() == 1);
							remoteToDo.setNote(remoteTask.getNote());
							remoteToDo.setPriority(remoteTask.getPriority());
							remoteToDo.setUserId(Long.parseLong(remoteTask.getUserId()));
							remoteToDo.setLastUpdated(Long.parseLong(remoteTask.getLastUpdated()));
							toDoItemData.updateItemCopy(remoteToDo);
						}
					}
				}
			}

			//delete tasks
			if(deleteList != null) {
				for (int x = 0; x < deleteList.size(); x++)
				{

					//
					timeout = 200;
					for (int inx = 0; inx < 10; inx++)
					{
						try {
							taskEndpoint.removeTaskInfo(Long.toString(deleteList.get(x))).execute();
							break;
						} catch (IOException e) {
							if (inx == 9)
								return Long.valueOf(1);
							else
							{
								try {
									Thread.sleep(timeout);
									timeout = timeout+timeout;
								} catch (InterruptedException e1) {
								}
							}
						}
					}

					//
				}
				//userData.updateSyncTime(userId);
			}
		}
		return Long.valueOf(0);
	}

	@Override
	protected void onPostExecute(Long result) {
		if (activity instanceof ToDoListActivity) {
			ToDoListActivity toDoListActivity = (ToDoListActivity) activity;
			List<ToDoItem> newList = toDoListActivity.getNewList();
			final ListView toDoListView = (ListView) toDoListActivity
					.findViewById(R.id.listView1);
			ToDoListAdapter adapter = (ToDoListAdapter) toDoListView
					.getAdapter();
			adapter.updateList(newList);
			adapter.notifyDataSetChanged();
		}
		super.onPostExecute(result);

		List<Users> userList = userData.getAllUsers();

		if (result == 0) {
			Toast.makeText(activity, "Done syncing tasks", Toast.LENGTH_SHORT)
			.show();

			for (int i = 0; i < userList.size(); i++) {
				long userId = userList.get(i).getId();
				userData.updateSyncTime(userId);
			}
		} else {
			Toast.makeText(activity, "Unable to sync tasks", Toast.LENGTH_SHORT)
			.show();
		}
	}

}
