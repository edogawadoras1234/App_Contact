package com.example.testdanhba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;

import android.content.DialogInterface;
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
    Button btnshow, btncall;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnshow = findViewById(R.id.btnshow);
        btncall = (Button) findViewById(R.id.btncall);

        btnshow.setOnClickListener(this);
        btncall.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btnadd)
            Toast.makeText(this, "Hello World", Toast.LENGTH_SHORT).show();
        else if (view.getId() == R.id.btnshow) {
            Intent intent = new Intent(this, phone.class);
            startActivity(intent);

        } else if (view.getId() == R.id.btncall) {
            Intent intent = new Intent(this, danhsach.class);
            startActivity(intent);
        }
    };
    public void DigalogThoat(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn thoát không?");
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
             System.exit(1);
            }
        });
        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }
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
                return true;
            case R.id.itemsua:
                Toast.makeText(this, "Choose Item Change", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemthoat:
                DigalogThoat();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}