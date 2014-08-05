package com.example.nittapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UpdateDB {
	
	public static final String KEY_TEXT = "_text";
		
	private static final String DATABASE_NAME = "MyUpdateDb.db";
	private static final String DATABASE_TABLE = "Updates1";
	private static final int DATABASE_VERSION = 1;
	

	private DbHelper ourHelper;
	private Context ourContext;
	private static SQLiteDatabase ourDatabase;

	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + "(" + KEY_TEXT
					+ " TEXT " + ")");
			// db.execSQL("create table MyEvents(_event text not null, _date text, _time text, _priority varchar(10));");

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
			// TODO Auto-generated method stub
			ourDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(ourDatabase);

		}

	}

	public UpdateDB(Context c) {
		this.ourContext = c;
		ourHelper = new DbHelper(ourContext);
	}

	public UpdateDB open() throws SQLException {
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		ourHelper.close();
	}

	public long createEntry(String name) {
		// TODO Auto-generated method stub

		ContentValues cv = new ContentValues();
		cv.put(KEY_TEXT, name);
				
		return ourDatabase.insert(DATABASE_TABLE, null, cv);

	}

	// public String getData() {
	public List getData() {
		// TODO Auto-generated method stub
		List <HashMap<String,String>>docList = new ArrayList<HashMap<String,String>>();
		String[] columns = new String[] { KEY_TEXT };
		
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);
		String result = "";
		System.out.println("no of records: " + c.getCount());
		int iEvent = c.getColumnIndex(KEY_TEXT);
		
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			
			HashMap temp = new HashMap();
			temp.put("text", c.getString(iEvent).toUpperCase());
			docList.add(temp);

		}
		return docList;
	}

	public void delete(String hMap) {
		// TODO Auto-generated method stub

		ourDatabase.delete(DATABASE_TABLE, "_name='" + hMap + "'", null);

	}

	public Cursor getDetails(String eventName) {
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_TEXT};
		Cursor m = ourDatabase.query(DATABASE_TABLE, columns, "_text='" + eventName + "'", null, null, null, null);
		return m;
	}
	
}