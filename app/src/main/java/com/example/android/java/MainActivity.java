package com.example.android.java;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.java.database.MyDataSource;
import com.example.android.java.utilities.ActivityHelper;

public class MainActivity extends AppCompatActivity {

    private ScrollView mScroll;
    private TextView mLog, mSearch;
    private static final String LOG_TEXT_KEY = "";
    private MyDataSource datasource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      Initialize the logging components
        mScroll = (ScrollView) findViewById(R.id.scrollLog);
        mLog = (TextView) findViewById(R.id.tvLog);
        mLog.setText("");

        mSearch = (TextView) findViewById(R.id.searchText);
        datasource = new MyDataSource(this);
        datasource.initData();
    }

    public void onRunBtnClick(View v) {
        searchDatabase(v);
        mSearch.setText("");
    }

    public void onClearBtnClick(View v) {
        mLog.setText("");
        mScroll.scrollTo(0, mScroll.getBottom());
    }

    public void displayMessage(String message) {
//        mLog.append(message + "\n");
        ActivityHelper.log(this, mLog, message, true);
        mScroll.scrollTo(0, mScroll.getBottom());

    }


    public void searchDatabase(View view) {

        String searchString = mSearch.getText().toString();

        if (searchString.length() == 0) {
            Toast.makeText(MainActivity.this, "Enter an integer between 1 and 100",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        int indexValue = new Integer(searchString);

        Cursor cursor = datasource.selectRecord(indexValue);
        if (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(MyDataSource.PERSON_NAME));
            String date = cursor.getString(cursor.getColumnIndex(MyDataSource.PERSON_DATE));
            String photo = cursor.getString(cursor.getColumnIndex(MyDataSource.PERSON_PHOTO));
            String revision = cursor.getString(cursor.getColumnIndex(MyDataSource.PERSON_REVISION));

            Toast.makeText(MainActivity.this, "You found " + name, Toast.LENGTH_SHORT).show();
            mLog.append("You found " + name  + " " + date + " "+ photo + " "+ revision+ "\n");

            ///de facut asta cat mai curand
        } else {
            Toast.makeText(MainActivity.this, "Person not found", Toast.LENGTH_SHORT).show();
            mLog.append("Person not found" + "\n");
        }
///ladida new comment
        //ladida another comment
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        ActivityHelper.log(this, mLog, "saving state", true);
        outState.putString(LOG_TEXT_KEY, mLog.getText().toString());
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mLog.setText(savedInstanceState.getString(LOG_TEXT_KEY));
        ActivityHelper.log(this, mLog, "restore state", true);

    }
}