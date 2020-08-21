package com.example.testdanhba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import static android.Manifest.permission.READ_CONTACTS;

public class danhsach extends AppCompatActivity{
    Database database;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    List<Contact> contactList;
    ContactAdapter contactAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        //Tạo đường gạch chân giữa các row
        DividerItemDecoration deviderItemDecoration = new DividerItemDecoration(this,layoutManager.getOrientation());
        recyclerView.addItemDecoration(deviderItemDecoration);

        swiptorefresh();
        Dexter.withActivity(this)
                .withPermission(READ_CONTACTS)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        if (response.getPermissionName().equals(READ_CONTACTS)){
                            getContact();
                            sort();
                        }
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(danhsach.this, "Need Permiss", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();;
    }
    private void getContact() {
        contactList = new ArrayList<>();
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null,null);
        contactList = new ArrayList<>();
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String avatar = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
            Contact contact = new Contact(name, phone, avatar);
            contactList.add(contact);
        }


        contactAdapter = new ContactAdapter(this, contactList);
        recyclerView.setAdapter(contactAdapter);
        contactAdapter.notifyDataSetChanged();
    }

    private void addContact() {
        contactList = new ArrayList<>();
        database = new Database(danhsach.this);
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null,null);
        contactList = new ArrayList<>();
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String avatar = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
            Contact contact = new Contact(name, phone, avatar);
            database.addData(name, phone, avatar);
            contactList.add(contact);
        }

        contactAdapter = new ContactAdapter(this, contactList);
        recyclerView.setAdapter(contactAdapter);
        contactAdapter.notifyDataSetChanged();
    }

    public void swiptorefresh(){
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                Toast.makeText(danhsach.this, "Refresh", Toast.LENGTH_SHORT).show();
                //load lai database
                //reload();
                //set lai adapter sau khi reset
                recyclerView.setAdapter(contactAdapter);
                contactAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    public void sort(){
        Collections.sort(contactList);
        contactAdapter.notifyDataSetChanged();
    }

    //load lai du lieu tu database len
//    public void reload(){
//        database = new Database(danhsach.this);
//        arrname = new ArrayList<>();
//        arrphone = new ArrayList<>();
//        arravatar = new ArrayList<>();
//        Cursor cursor = database.readAllData();
//        if (cursor.getCount() == 0){
//            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
//        } else {
//            while (cursor.moveToNext()){
//                arrname.add(cursor.getString(0));
//                arrphone.add(cursor.getString(1));
//                arravatar.add(cursor.getString(2));
//                phoneAdapter = new PhoneAdapter(this, arrname, arrphone, arravatar);
//                phoneAdapter.notifyDataSetChanged();
//            }
//        }
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemadd:
                Intent intent = new Intent(this,add_phone_number.class);
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