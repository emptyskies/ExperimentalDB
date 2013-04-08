package com.example.textura.db;

import com.example.textura.DbActivity;
import com.example.textura.bean.TaskBean;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBHelper extends SQLiteOpenHelper {
	public static final String DB_NAME = "Task.db";
	public static final String TASK_TABLE_NAME = "Task_list";
	public static final int DB_VERSION = 1;
	public static final String CREATE_DB = "CREATE TABLE " + TASK_TABLE_NAME + "(" // --------------------------------------------------------------------
			+ "id INTEGER PRIMARY KEY AUTOINCREMENT," // ------------------------------------------------
			+ "taskId INTEGER NOT NULL," // ------------------------------------------------
			+ "name TEXT," // ------------------------------------------------------------------------
			+ "detail TEXT," // ------------------------------------------------------------------------
			+ "classificationName TEXT," // ------------------------------------------------------------------------
			+ "classificationId INTEGER," // ------------------------------------------------------------------------
			+ "classificationColor INTEGER," // ------------------------------------------------------------------------
			+ "year INTEGER," // ------------------------------------------------------------------------
			+ "mouth INTEGER," // ------------------------------------------------------------------------
			+ "day INTEGER," // ------------------------------------------------------------------------
			+ "hour INTEGER," // ------------------------------------------------------------------------
			+ "minute INTEGER," // ------------------------------------------------------------------------
			+ "priority INTEGER,"//-----------------------------------------------------------------------
			+ "state INTEGER"
			+ ")";// --------------------------------------------------------------------

	public MyDBHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//Comply the SQL and create the Database
		db.execSQL(CREATE_DB);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		/*Log.w(MyDBHelper.class.getName(), "Upgrading database. Destroying old for new.");
		db.execSQL("DROP TABLE IF EXISTS " + TASK_TABLE_NAME);
		onCreate(db);*/
	}

	/**
	 * Check the database
	 * @param selection
	 * @param selectionArgs
	 * @return
	 */
	public Cursor query(String selection, String[] selectionArgs) {
		Cursor cursor = null;
		SQLiteDatabase db = getWritableDatabase();
		if (db != null) {
			try {
				cursor = db.query(TASK_TABLE_NAME, null, selection, selectionArgs, null, null, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cursor;
	}

	/**
	 * Add data
	 * @param taskBean
	 * @return
	 */
	public boolean add(TaskBean taskBean) {
		SQLiteDatabase db = getWritableDatabase();
		if (db != null) {
			try {
				long result = db.insert(TASK_TABLE_NAME, null, taskBean.change2ContentValues());
				if (result != -1) {
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	/**
	 * Update data
	 * @param taskBean
	 * @return
	 */
	public boolean update(TaskBean taskBean) {
		SQLiteDatabase db = getWritableDatabase();
		if (db != null) {
			try {
				long result = db.update(TASK_TABLE_NAME, taskBean.change2ContentValues(), "taskId=?",
						new String[] { String.valueOf(taskBean.getId()) });
				if (result != -1) {
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return false;
	}
}
