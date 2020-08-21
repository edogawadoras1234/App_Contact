package com.example.testdanhba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.ContentValues;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import static android.Manifest.permission.CALL_PHONE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    SQLiteDatabase db;
    EditText user, pass;
    ListView listView;
    Button btnshow, btnadd, btncall;
    ContentValues val = new ContentValues();
    String arruser[], arrpass[];
    FlowMenu flowMenu;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = findViewById(R.id.edtname);
        pass = findViewById(R.id.edtpass);
        btnshow = findViewById(R.id.btnshow);
        btnadd = findViewById(R.id.btnadd);
        listView = findViewById(R.id.listphone);
        btncall = (Button) findViewById(R.id.btncall);


        db = openOrCreateDatabase("student.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        String query = "create table IF NOT EXISTS login(user text,pass text)";
        db.execSQL(query);
        btnadd.setOnClickListener(this);
        btnshow.setOnClickListener(this);
        btncall.setOnClickListener(this);

    }

    public void onClick(View view) {

        if (view.getId() == R.id.btnadd)
            try {
                if (user.length() == 0 && pass.length() == 0)
                    Toast.makeText(this, "Fill Text", Toast.LENGTH_SHORT).show();
                else if (user.length() == 0 || pass.length() == 0)
                    Toast.makeText(this, "fill user or text", Toast.LENGTH_SHORT).show();
                else {
                    val.put("Name", user.getText().toString());
                    val.put("Phone", pass.getText().toString());
                    long l = db.insert("login", null, val);
                    if (l > 0)
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this, "Try Again", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        else if (view.getId() == R.id.btnshow) {
            Intent intent = new Intent(this, phone.class);
            startActivity(intent);

        } else if (view.getId() == R.id.btncall) {
            Intent intent = new Intent(this, danhsach.class);
            startActivity(intent);
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemadd:
                Intent intent = new Intent(this, add_phone_number.class);
                startActivity(intent);
                Toast.makeText(this, "Choose Item Add", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemsua:
                Toast.makeText(this, "Choose Item Change", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemthoat:
                System.exit(1);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}