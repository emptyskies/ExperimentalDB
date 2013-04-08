package com.example.textura;
import java.util.ArrayList;
import java.util.List;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbActivity extends SQLiteOpenHelper  {

	
	private static final String DATABASE_NAME = "textura.db";
	private static final int DATABASE_VERSION = 1;
	
	public static final String COLUMN_ID = "_id";
	
	// public table information for task
	public static final String TABLE_TASK = "task";
	public static final String COLUMN_TaskId = "ID";
	public static final String COLUMN_Name = "name";
	public static final String COLUMN_classificationName = "classificationName";
	public static final String COLUMN_classificationId = "classificationId";
	public static final String COLUMN_classificationColor = "classificationColor";
	public static final String COLUMN_year = "year";
	public static final String COLUMN_mouth = "mouth";
	public static final String COLUMN_day = "day";
	public static final String COLUMN_hour = "hour";
	public static final String COLUMN_minute = "minute";
	public static final String COLUMN_priority = "priority";
	public static final String COLUMN_state = "state";
	
	
	private static final String CREATE_TASK = "create table "
			+ TABLE_TASK + "(" + COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_TaskId + " text not null, " + COLUMN_Name + " text not null, "
			+ COLUMN_classificationName + " text not null, " + COLUMN_classificationId + " text not null, "
			+ COLUMN_classificationColor + " text not null, " + COLUMN_year + " text not null, "
			+ COLUMN_mouth + " text not null, " + COLUMN_day + " text not null, "
			+ COLUMN_hour + " text not null, " + COLUMN_minute + " text not null, "
			+ COLUMN_priority + " text not null, " + COLUMN_state + " text not null, "
			+ COLUMN_classificationColor + " text not null);";
	
	
	
	public DbActivity (Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}



	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TASK);	
	}



	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w(DbActivity.class.getName(), "Upgrading database. Destroying old for new.");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
		onCreate(db);
		
	}
	
	
	
}

