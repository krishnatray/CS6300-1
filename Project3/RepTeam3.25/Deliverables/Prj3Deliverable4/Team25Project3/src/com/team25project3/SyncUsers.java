package com.team25project3;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;
import com.team25project3.database.SyncDataSource;
import com.team25project3.database.UserDataSource;
import com.team25project3.database.Users;
import com.team25project3.userendpoint.Userendpoint;
import com.team25project3.userendpoint.model.User;

public class SyncUsers extends AsyncTask<Void, Integer, Long> {

	private Context context = null;

	public SyncUsers(Context applicationContext) {
		context = applicationContext;
	}	
	
	@Override
	protected Long doInBackground(Void... voids) {
		
		Userendpoint.Builder endpointBuilder = new Userendpoint.Builder(
				AndroidHttp.newCompatibleTransport(),
				new JacksonFactory(),
				new HttpRequestInitializer() {
					@Override
					public void initialize(HttpRequest httpRequest) {
					}
				});
	
		SyncDataSource syncData = new SyncDataSource(context);
	    syncData.open();
	    
	    UserDataSource userData = new UserDataSource(context);
	    userData.open();
			
		Userendpoint endpoint = CloudEndpointUtils.updateBuilder(
		endpointBuilder).build();
	
		long lastSyncTime = syncData.getLastSyncTime();
		List <Users> userList = userData.getAllUsersForSync(lastSyncTime);
				
		for (int i = 0; i < userList.size(); i++)
		{	
			Users U = userList.get(i);
		    User uinfo = new User().setName(U.getName());
		    uinfo.setId(String.valueOf(U.getId()));	
		    uinfo.setPassword(U.getPwd());
		    uinfo.setLastUpdated(String.valueOf(U.getLastUpdated()));
		    	
		    //Post the record
	    	int timeout = 200;
		    for (int inx = 0; inx < 10; inx++)
		    {
				try {
				    endpoint.insertRegUserInfo(uinfo).execute();
					syncData.updateSyncTime();
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
				return Long.valueOf(0);
	}
	
	@Override
	protected void onPostExecute(Long result) {
		super.onPostExecute(result);
		if(result == 0) {
			Toast.makeText(context, "Done syncing users", Toast.LENGTH_SHORT).show();
		}
		else {
			Toast.makeText(context, "Unable to sync users", Toast.LENGTH_SHORT).show();
		}
    }

}

