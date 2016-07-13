package com.example.android.java.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by alexandru.pavel on 09.05.2016.
 */
public class MyDataSource {
    private MyDatabaseHelper dbHelper;

    private SQLiteDatabase database;

    public final static String PEOPLE_TABLE = "people"; // name of table
    public final static String PERSON_ID = "personId"; // id value for employee
    public final static String PERSON_NAME = "personName";  // name of employee
    public final static String PERSON_DATE = "personDate"; //
    public final static String PERSON_REVISION = "personRevision"; //
    public final static String PERSON_PHOTO = "personPhoto";  //


    public MyDataSource(Context context) {
        dbHelper = new MyDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public long createRecord(int id, String name, String date, String revision, String photo) {
        ContentValues values = new ContentValues();
        values.put(PERSON_ID, id);
        values.put(PERSON_NAME, name);
        values.put(PERSON_DATE, date);
        values.put(PERSON_REVISION, revision);
        values.put(PERSON_PHOTO, photo);

        return database.insert(PEOPLE_TABLE, null, values);
    }

    public Cursor selectRecord(int personId) {
        String[] cols = new String[]{PERSON_ID, PERSON_NAME, PERSON_DATE, PERSON_PHOTO, PERSON_REVISION};
        return database.query(true, PEOPLE_TABLE, cols,
                MyDataSource.PERSON_ID + "=" + personId,
                null, null, null, null, null);
    }

    public void initData() {
        long count = DatabaseUtils.queryNumEntries(database, PEOPLE_TABLE);
        if (count == 0) {
            Log.i("MyDataSource", "Initializing data");
            for (int i = 1; i <= 100; i++) {
                createRecord(i, "Person " + i, "PERSON_DATE " + i, "PERSON_REVISION " +i , "PERSON_PHOTO " +i);
            }
            Log.i("MyDataSource", "Data  initialized ");
        } else {
            Log.i("MyDataSource", "Data already initialized with " + count + " rows");
        }
    }

}