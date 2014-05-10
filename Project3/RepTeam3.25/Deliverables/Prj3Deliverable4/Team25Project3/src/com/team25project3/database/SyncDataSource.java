package com.team25project3.database;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SyncDataSource {

	  // Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
	      MySQLiteHelper.COLUMN_SYNC, MySQLiteHelper.COLUMN_USERID
	      };
	  

	  public SyncDataSource(Context context) {
	    dbHelper = new MySQLiteHelper(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }
	  
	  
	  public long getLastSyncTime()
	  {
		  Cursor cursor = database.query(MySQLiteHelper.TABLE_SYNC,
			        allColumns, MySQLiteHelper.COLUMN_ID + " = " + 0, null,
			        null, null, null);
		    cursor.moveToFirst();
			  if (cursor.isAfterLast())
			  {
				  ContentValues values = new ContentValues();
				  values.put(MySQLiteHelper.COLUMN_SYNC, 0);
				  values.put(MySQLiteHelper.COLUMN_ID, 0);
				  values.put(MySQLiteHelper.COLUMN_USERID, 0);

				  database.insert(MySQLiteHelper.TABLE_SYNC, null,
					        values);
				  
				  return 0;
			  }

		    long lastSync = cursor.getLong(1);
		    return lastSync;  
	  }
	  
	  
	  public void updateSyncTime()
	  {
			  long syncTime = new Date().getTime();
			  ContentValues values = new ContentValues();
			  values.put(MySQLiteHelper.COLUMN_SYNC, syncTime);
			  
			  database.update(MySQLiteHelper.TABLE_SYNC, values, MySQLiteHelper.COLUMN_ID + " = " + 0, null);
	  }
	  
	  public long getUserId()
	  {
		  Cursor cursor = database.query(MySQLiteHelper.TABLE_SYNC,
			        allColumns, MySQLiteHelper.COLUMN_ID + " = " + 0, null,
			        null, null, null);
		  cursor.moveToFirst();
		  if (cursor.isAfterLast())
		  {
			  ContentValues values = new ContentValues();
			  values.put(MySQLiteHelper.COLUMN_SYNC, 0);
			  values.put(MySQLiteHelper.COLUMN_ID, 0);
			  
			  database.insert(MySQLiteHelper.TABLE_SYNC, null,
				        values);
			  
			  return 0;
		  }

	    long userId = cursor.getLong(2);
	    return userId;
	 }
	  
	  public void updateUserId(long userId)
	  {
			  ContentValues values = new ContentValues();
			  values.put(MySQLiteHelper.COLUMN_USERID, userId);
			  
			  database.update(MySQLiteHelper.TABLE_SYNC, values, MySQLiteHelper.COLUMN_ID + " = " + 0, null);
	  }

}

