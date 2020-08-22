package com.example.testdanhba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class phone extends AppCompatActivity implements DanhBaCLickInterfact {
    Database database;
    RecyclerView rvDanhBa;
    DanhBaAdapter danhBaAdapter;
    ArrayList arrname,arrphone,arravatar;
    SwipeRefreshLayout swipeRefreshLayout;
    DanhBaCLickInterfact danhBaCLickInterfact;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        rvDanhBa = findViewById(R.id.rv_danhba);
        rvDanhBa.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvDanhBa.setLayoutManager(layoutManager);
        //Tạo đường gạch chân giữa các row
        DividerItemDecoration deviderItemDecoration = new DividerItemDecoration(this,layoutManager.getOrientation());
        rvDanhBa.addItemDecoration(deviderItemDecoration);
        getSupportActionBar().setTitle("Danh Bạ Database");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loaddata();
        swiptorefresh();
    }
    //load lai du lieu tu database len
    public void loaddata(){
        database = new Database(phone.this);
        arrname = new ArrayList<>();
        arrphone = new ArrayList<>();
        arravatar = new ArrayList<>();
        Cursor cursor = database.readAllData();
        if (cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                arrname.add(cursor.getString(0));
                arrphone.add(cursor.getString(1));
                arravatar.add(cursor.getString(2));
                danhBaAdapter= new DanhBaAdapter(phone.this, arrname, arrphone, arravatar,this);
            }
            rvDanhBa.setAdapter(danhBaAdapter);
            danhBaAdapter.notifyDataSetChanged();
        }
    }
   
    public void swiptorefresh(){
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                Toast.makeText(phone.this, "Refresh", Toast.LENGTH_SHORT).show();
                //load lai database
                loaddata();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemadd:
                Intent intent = new Intent(this,add_phone_number.class);
                startActivity(intent);
                return true;
            case R.id.itemsua:
                Toast.makeText(this, "Sửa", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemthoat:
                DigalogThoat();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onItemClick(int position) {
        String ten = (String) arrname.get(position);
        Toast.makeText(this, "" + arrname.get(position), Toast.LENGTH_SHORT).show();
    }
    public void DialogSua() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit_phone);
        final EditText edtname, edtphone, edtavatar;
        Button btnaccept, btncancle;
        edtname = dialog.findViewById(R.id.editName_dialog);
        edtphone = dialog.findViewById(R.id.editPhone_dialog);
        edtavatar = dialog.findViewById(R.id.editAvatar_dialog);

            btnaccept = dialog.findViewById(R.id.btnchange_dgl_change);
            btncancle = dialog.findViewById(R.id.btncl_dgl_change);

            btncancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            btnaccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (edtphone.length() == 0) {
                        Toast.makeText(phone.this, "Không được bỏ trống số điện thoại", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        database.UpdateData(edtname.getText().toString(), edtphone.getText().toString(), edtavatar.getText().toString());
                        Toast.makeText(phone.this, "Đã thay đổi thành công", Toast.LENGTH_SHORT).show();
                    }
                    loaddata();
                }
            });
            dialog.show();
        }
    }