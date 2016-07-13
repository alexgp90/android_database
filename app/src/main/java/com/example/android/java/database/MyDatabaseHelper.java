package com.example.android.java.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by alexandru.pavel on 09.05.2016.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "people";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE =
            "CREATE TABLE " + MyDataSource.PEOPLE_TABLE + " ("
                             + MyDataSource.PERSON_ID + " INTEGER PRIMARY KEY, "
                              + MyDataSource.PERSON_NAME + " TEXT NOT NULL, "
                                + MyDataSource.PERSON_DATE + " TEXT NOT NULL, "
                                + MyDataSource.PERSON_REVISION +" TEXT NOT NULL, "
                                + MyDataSource.PERSON_PHOTO + " TEXT NOT NULL );";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    // Method is called during an upgrade of the database,
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + MyDataSource.PEOPLE_TABLE);
        onCreate(database);
    }
}