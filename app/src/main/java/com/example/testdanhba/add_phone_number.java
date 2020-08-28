package com.example.testdanhba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class add_phone_number extends AppCompatActivity implements View.OnClickListener {
    Button btnadd, btncancle;
    EditText edtname, edtphone, edtavatar;
    Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone_number);
        edtname = findViewById(R.id.edtname);
        edtphone = findViewById(R.id.edtphone);
        edtavatar = findViewById(R.id.edtavata);
        btnadd = findViewById(R.id.btnadd);
        btncancle = findViewById(R.id.btncancle);
        btnadd.setOnClickListener(this);
        btncancle.setOnClickListener(this);
        getSupportActionBar().setTitle("Thêm Danh Ba");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnadd){
        database = new Database(this);
        if(edtname.length()==0 && edtphone.length()==0) {
            Toast.makeText(this, "Xin hãy nhập tên và số điện thoại", Toast.LENGTH_SHORT).show();
        }
        else if (edtname.length()==0 || edtphone.length()==0) {
            Toast.makeText(this, "Không được bỏ trống Tên hoặc số điện thoại", Toast.LENGTH_SHORT).show();
        }
        else {
            database.addData(null,edtname.getText().toString(), edtphone.getText().toString(), edtavatar.getText().toString());
            Toast.makeText(this, "Add Success", Toast.LENGTH_SHORT).show();
        }
        }else if (view.getId() == R.id.btncancle){
            System.exit(1);
        }
    }
}