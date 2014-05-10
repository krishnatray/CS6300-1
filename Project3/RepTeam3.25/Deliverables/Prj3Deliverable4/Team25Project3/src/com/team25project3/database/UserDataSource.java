package com.team25project3.database;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UserDataSource {

	  // Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
	      MySQLiteHelper.COLUMN_NAME,
	      MySQLiteHelper.COLUMN_PASSWORD,
	      MySQLiteHelper.COLUMN_LASTUPDATED,
	      MySQLiteHelper.COLUMN_SYNC,
	      };
	  

	  public UserDataSource(Context context) {
	    dbHelper = new MySQLiteHelper(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }
	  
	  public Users selectUser(String name) {
		    Cursor cursor = database.query(MySQLiteHelper.TABLE_USERS,
			        allColumns, MySQLiteHelper.COLUMN_NAME + " = \"" + name+"\"", null,
			        null, null, null);
		    cursor.moveToFirst();
			  if (cursor.isAfterLast())
				  return null;
						  Users newUser = cursorToUsers(cursor);
		  return newUser;
	  }
	  
	  public long getLastSyncTime(long uID)
	  {
		  Cursor cursor = database.query(MySQLiteHelper.TABLE_USERS,
			        allColumns, MySQLiteHelper.COLUMN_ID + " = " + uID, null,
			        null, null, null);
		    cursor.moveToFirst();
			  if (cursor.isAfterLast())
				  return -1;
		    long lastSync = cursor.getLong(4);
		   return lastSync;  
	  }
	  
	  public Users getUserById(long id) {
		    Cursor cursor = database.query(MySQLiteHelper.TABLE_USERS,
			        allColumns, MySQLiteHelper.COLUMN_ID + " = " + id, null,
			        null, null, null);
		    cursor.moveToFirst();
			  if (cursor.isAfterLast())
				  return null;
						  Users newUser = cursorToUsers(cursor);
		  return newUser;		  
	  }

	  public int updateUsers(Users user) {
		  long id = user.getId();
		  
		  long updateTime = new Date().getTime();
		  
		    ContentValues values = new ContentValues();
		    values.put(MySQLiteHelper.COLUMN_NAME, user.getName());
		    values.put(MySQLiteHelper.COLUMN_PASSWORD, user.getPwd());
		    values.put(MySQLiteHelper.COLUMN_LASTUPDATED, updateTime);
		    values.put(MySQLiteHelper.COLUMN_SYNC, user.getLastSync());
		  
		  return database.update(MySQLiteHelper.TABLE_USERS, values, MySQLiteHelper.COLUMN_ID + " = " + id, null);

	  }
	  
	  public void updateSyncTime(long uID)
	  {
		  	  Users user = getUserById(uID);
			  long syncTime = new Date().getTime();
			  ContentValues values = new ContentValues();
			  values.put(MySQLiteHelper.COLUMN_NAME, user.getName());
			  values.put(MySQLiteHelper.COLUMN_PASSWORD, user.getPwd());
			  values.put(MySQLiteHelper.COLUMN_LASTUPDATED, user.getLastUpdated());
			  values.put(MySQLiteHelper.COLUMN_SYNC, syncTime);
			  
			  database.update(MySQLiteHelper.TABLE_USERS, values, MySQLiteHelper.COLUMN_ID + " = " + uID, null);
	  }
	  
	  public Users createUsers(String name, String pwd) {
	    ContentValues values = new ContentValues();
	    
	    long syncTime = new Date().getTime();
	    values.put(MySQLiteHelper.COLUMN_ID, syncTime);
	    values.put(MySQLiteHelper.COLUMN_NAME, name);
	    values.put(MySQLiteHelper.COLUMN_PASSWORD, pwd);
	    values.put(MySQLiteHelper.COLUMN_LASTUPDATED, syncTime);
	    values.put(MySQLiteHelper.COLUMN_SYNC, syncTime);
	    
	    long insertId = database.insert(MySQLiteHelper.TABLE_USERS, null,
	        values);
	    Cursor cursor = database.query(MySQLiteHelper.TABLE_USERS,
	        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Users newUser = cursorToUsers(cursor);
	    cursor.close();
	    
	    return newUser;
	  }

	  public void deleteUser(Users user) {
	    long id = user.getId();
	    System.out.println("User deleted with id: " + id);
	    database.delete(MySQLiteHelper.TABLE_USERS, MySQLiteHelper.COLUMN_ID
	        + " = " + id, null);
	    
	  }

	  public List<Users> getAllUsers() {
	    List<Users> allusers = new ArrayList<Users>();

	    Cursor cursor = database.query(MySQLiteHelper.TABLE_USERS,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Users user = cursorToUsers(cursor);
	      allusers.add(user);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return allusers;
	  }
	  
	  public List<Users> getAllUsersForSync(long lastSyncTime) {
		    List<Users> allusers = new ArrayList<Users>();

		    Cursor cursor = database.query(MySQLiteHelper.TABLE_USERS,
			        allColumns, MySQLiteHelper.COLUMN_LASTUPDATED + " > " + lastSyncTime, null,
			        null, null, null);

		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		      Users user = cursorToUsers(cursor);
		      allusers.add(user);
		      cursor.moveToNext();
		    }
		    // Make sure to close the cursor
		    cursor.close();
		    return allusers;
		  }

	  private Users cursorToUsers(Cursor cursor) {
		  Users user = new Users();
		  user.setId(cursor.getLong(0));
		  user.setName(cursor.getString(1));
		  user.setPwd(cursor.getString(2));
		  user.setLastUpdated(cursor.getLong(3));
		  user.setLastSync(cursor.getLong(4));
	    return user;
	  }
}
