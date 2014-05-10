package com.team25project3.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
	
	  public static final String TABLE_COMMENTS = "comments";
	  public static final String TABLE_USERS = "users";
	  public static final String TABLE_TODOLIST = "todolist";
	  public static final String TABLE_SYNC = "sync";
	  
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_NAME = "name";
	  public static final String COLUMN_PASSWORD = "pwd";
	  public static final String COLUMN_LASTUPDATED = "lastupdate";
	  public static final String COLUMN_SYNC = "lastsync";
	  

	  public static final String COLUMN_USERID = "userid";
	  public static final String COLUMN_TODONAME = "name";
	  public static final String COLUMN_TODONOTE = "note";
	  public static final String COLUMN_DUETIME = "duetime";
	  public static final String COLUMN_NODUETIME = "noduetime";
	  public static final String COLUMN_CHECKED = "checked";
	  public static final String COLUMN_PRIORITY = "priority";

	  
	  public static final String COLUMN_COMMENT = "comment";
	  
	  

	  public static final String DATABASE_NAME = "team25todlist-V4.db";
	  private static final int DATABASE_VERSION = 1;

	  private static final String USER_DATABASE_CREATE = "create table "
			  + TABLE_USERS + "(" + COLUMN_ID
			  + " integer primary key autoincrement, " + COLUMN_NAME
			  + " text not null, " + COLUMN_PASSWORD
			  + " text not null, " + COLUMN_LASTUPDATED
			  + " integer default 0, " + COLUMN_SYNC
			  + " integer default 0);";

	  private static final String TODOLIST_DATABASE_CREATE = "create table "
			  + TABLE_TODOLIST + "(" + COLUMN_ID
			  + " integer primary key autoincrement, " + COLUMN_USERID
			  + " integer default 0, " + COLUMN_TODONAME
			  + " text , " + COLUMN_TODONOTE
			  + " text , " + COLUMN_DUETIME
			  + " integer default 0, " + COLUMN_NODUETIME
			  + " integer default 0, " + COLUMN_PRIORITY
			  + " integer default 0, " + COLUMN_CHECKED
			  + " integer default 0, " + COLUMN_LASTUPDATED
			  + " integer default 0);";

	  
	  
	  private static final String DATABASE_CREATE = "create table "
		      + TABLE_COMMENTS + "(" + COLUMN_ID
		      + " integer primary key autoincrement, " + COLUMN_COMMENT
		      + " text not null);";
	  
	  private static final String SYNC_DATABASE_CREATE = "create table "
		      + TABLE_SYNC + "(" + COLUMN_ID
		      + " integer primary key autoincrement, " + COLUMN_SYNC
		      + " integer default 0, " + COLUMN_USERID
		      + " integer default 0);";


	  
	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
		database.execSQL(USER_DATABASE_CREATE);
		database.execSQL(TODOLIST_DATABASE_CREATE);
		database.execSQL(SYNC_DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(MySQLiteHelper.class.getName(),
	            "Upgrading database from version " + oldVersion + " to "
	                + newVersion + ", which will destroy all old data");
	        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
	        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
	        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOLIST);
	        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SYNC);
	        onCreate(db);
	}

}
