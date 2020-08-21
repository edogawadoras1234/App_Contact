package com.example.testdanhba;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class FlowMenu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                Toast.makeText(this, "Choose Item Add", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemsua:
                Toast.makeText(this, "Choose Item Change", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemthoat:
                Toast.makeText(this, "Thoát Danh Bạ", Toast.LENGTH_SHORT).show();
                System.exit(1);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
