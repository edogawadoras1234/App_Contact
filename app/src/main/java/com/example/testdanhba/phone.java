package com.example.testdanhba;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class phone extends AppCompatActivity {
    ListView lst;
    ContentValues val = new ContentValues();
    String arruser[],arrpass[];
    SQLiteDatabase db;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        lst = (ListView) findViewById(R.id.listphone);

        db=openOrCreateDatabase("student.db",SQLiteDatabase.CREATE_IF_NECESSARY,null);
        Cursor c = db.query("login",null,null,null,null,null,null);
        arruser = new String [c.getCount()];
        arrpass = new String [c.getCount()];
        c.moveToFirst();
        for(int i =0;i<arruser.length;i++)
        {
            arruser[i]=c.getString(0);
            arrpass[i]=c.getString(1);
            c.moveToNext();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arruser);
        lst.setAdapter(adapter);
        //CLick để show thông tin qua tab mới
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "Number:"+ arrpass[i],Toast.LENGTH_LONG).show();
            }
        });
    }
}