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
import android.util.Log;

public class EventDb {

	public static final String KEY_DATE = "_date";
	public static final String KEY_NAME = "_name";
	public static final String KEY_TIME = "_time";
	public static final String KEY_DESC = "_desc";
	public static final String KEY_VENUE = "_venue";
	public static final String KEY_ORGANISATIONNAME = "_orgname";
	public static final String KEY_EVENTID = "_eventid";
	//public static final String KEY_CLUSTERID = "_clusterid";
	public static final String KEY_CLUSTERNAME = "_clustername";
	public static final String KEY_UPDATE = "_update";
	public static final String KEY_DELETE = "_delete";
	// public static final String KEY_DELETED = "_deleted";

	// public static final String KEY_CHEMAIL = "_chemail";
	// public static final String KEY_PIC = "_pic";

	private static final String DATABASE_NAME = "MyEventDb";
	private static final String DATABASE_TABLE = "Events1";
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
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + "(" + KEY_NAME
					+ " TEXT NOT NULL, " + KEY_DATE + " TEXT, " + KEY_TIME
					+ " TEXT, " + KEY_VENUE + " TEXT, " + KEY_ORGANISATIONNAME
					+ " TEXT, " + KEY_EVENTID + " TEXT, " + KEY_CLUSTERNAME + " TEXT, " + KEY_DELETE
					+ " TEXT, " + KEY_UPDATE + " TEXT, " + KEY_DESC + " TEXT);");
			// db.execSQL("create table MyEvents(_event text not null, _date text, _time text, _priority varchar(10));");

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
			// TODO Auto-generated method stub
			ourDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(ourDatabase);

		}

	}

	public EventDb(Context c) {
		this.ourContext = c;
		ourHelper = new DbHelper(ourContext);
	}

	public EventDb open() throws SQLException {
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		ourHelper.close();
	}

	public long createEntry(String name, String date, String time, String desc,
			String venue, String org, String eventid,
			String clustername, String delete, String update) {
		// TODO Auto-generated method stub

		ContentValues cv = new ContentValues();
		cv.put(KEY_NAME, name);
		cv.put(KEY_DATE, date);
		cv.put(KEY_TIME, time);
		cv.put(KEY_DESC, desc);
		cv.put(KEY_VENUE, venue);
		cv.put(KEY_ORGANISATIONNAME, org);
		cv.put(KEY_EVENTID, eventid);
		//cv.put(KEY_CLUSTERID, clusterid);
		cv.put(KEY_CLUSTERNAME, clustername);
		cv.put(KEY_DELETE, delete);
		cv.put(KEY_UPDATE, update);

		return ourDatabase.insert(DATABASE_TABLE, null, cv);

	}

	// public String getData() {
	public List getEventDetails(String eventid) {
		// TODO Auto-generated method stub
		List docList = new ArrayList();
		String[] columns = new String[] { KEY_NAME, KEY_DATE, KEY_TIME,
				KEY_VENUE, KEY_DESC };

		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, "_eventid='"
				+ eventid + "'", null, null, null, null);

		System.out.println("no of records: " + c.getCount());
		int iEvent = c.getColumnIndex(KEY_NAME);
		int iDate = c.getColumnIndex(KEY_DATE);
		int iTime = c.getColumnIndex(KEY_TIME);
		int iDesc = c.getColumnIndex(KEY_DESC);
		int iVenue = c.getColumnIndex(KEY_VENUE);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			// result = result + c.getString(iEvent) + " " + c.getString(iDate)
			// + " " + c.getString(iTime) + " " + c.getString(iP) + "\n" ;
			HashMap temp = new HashMap();
			temp.put("name", c.getString(iEvent).toUpperCase());
			temp.put("date", c.getString(iDate));
			temp.put("time", c.getString(iTime));
			temp.put("desc", c.getString(iDesc));
			temp.put("venue", c.getString(iVenue));
			// temp.put("pic", c.getInt(iPic));

			docList.add(temp);

		}
		return docList;
	}

	public void delete(String eventid) {
		// TODO Auto-generated method stub

		ourDatabase.delete(DATABASE_TABLE, "_eventid='" + eventid + "'", null);

	}

	public List getEvents(String clustername) {
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_NAME };
		Cursor m = ourDatabase.query(DATABASE_TABLE, columns, "_clustername='"
				+ clustername + "'", null, null, null, KEY_NAME + " ASC");
		List docList = new ArrayList<String>();
		int iEvent = m.getColumnIndex(KEY_NAME);

		for (m.moveToFirst(); !m.isAfterLast(); m.moveToNext()) {

			docList.add(m.getString(iEvent));
		}

		return docList;
	}

	public List getClusters(String orgid) {
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_CLUSTERNAME };
		List docList = new ArrayList<String>();
		Cursor m = ourDatabase.query(DATABASE_TABLE, columns, "_orgname='"
				+ orgid + "'", null, KEY_CLUSTERNAME, null, KEY_CLUSTERNAME
				+ " ASC");
		int iEvent = m.getColumnIndex(KEY_CLUSTERNAME);

		for (m.moveToFirst(); !m.isAfterLast(); m.moveToNext()) {

			docList.add(m.getString(iEvent));
		}

		return docList;
	}

	public List getOrgList() {
		// TODO Auto-generated method stub

//		List docList = new ArrayList<String>();
//
//		docList.add("Admin");
//		docList.add("Festember");
//		docList.add("Pragyan");
//
//		return docList;
		String[] columns = new String[] { KEY_ORGANISATIONNAME };
		List docList = new ArrayList<String>();
		Cursor m = ourDatabase.query(DATABASE_TABLE, columns, null, null, KEY_ORGANISATIONNAME, null, KEY_ORGANISATIONNAME
				+ " ASC");
		int iEvent = m.getColumnIndex(KEY_ORGANISATIONNAME);

		for (m.moveToFirst(); !m.isAfterLast(); m.moveToNext()) {

			docList.add(m.getString(iEvent));
		}

		return docList;
	}
/*
	public int getClusterId(String name) {
		String itemname = null;
		String[] columns = new String[] { KEY_CLUSTERID };
		Cursor m = ourDatabase.query(DATABASE_TABLE, columns, "_clustername='"
				+ name + "'", null, null, null, null);
		Log.e("Cursor", m.toString());
		int iEvent = m.getColumnIndex(KEY_CLUSTERID);
		for (m.moveToFirst(); !m.isAfterLast(); m.moveToNext()) {

		 itemname=m.getString(iEvent);
		}
		Log.e(itemname, "Cluster id");
		return Integer.parseInt(itemname);
	}
	*/
	
	public int getEventId(String name) {
		String itemname = null;
		String[] columns = new String[] { KEY_EVENTID };
		Cursor m = ourDatabase.query(DATABASE_TABLE, columns, "_name='"
				+ name + "'", null, null, null, null);
		
		int iEvent = m.getColumnIndex(KEY_EVENTID);
		for (m.moveToFirst(); !m.isAfterLast(); m.moveToNext()) {

		 itemname=m.getString(iEvent);
		}
		
		return Integer.parseInt(itemname);
	}
	
	public int checkId(String name) {
		String itemname = null;
		String[] columns = new String[] { KEY_EVENTID };
		Cursor m = ourDatabase.query(DATABASE_TABLE, columns, "_eventid='"
				+ name + "'", null, null, null, null);
		//Log.e("Cursor", m.toString());
		int iEvent = m.getColumnIndex(KEY_EVENTID);
		for (m.moveToFirst(); !m.isAfterLast(); m.moveToNext()) {

		 itemname=m.getString(iEvent);
		}
		
		if(itemname!=null)
		return Integer.parseInt(itemname);
		else
			return 0;
	}

}