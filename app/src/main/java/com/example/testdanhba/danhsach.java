package com.example.testdanhba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

public class danhsach extends AppCompatActivity implements DanhBaCLickInterfact {
    Database database;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    List<Contact> contactList;
    ContactAdapter contactAdapter;
    DanhBaCLickInterfact danhBaCLickInterfact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        //Tạo đường gạch chân giữa các row
        DividerItemDecoration deviderItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerView.addItemDecoration(deviderItemDecoration);
        swiptorefresh();

        new ItemTouchHelper(itemtouchhelper).attachToRecyclerView(recyclerView);
        Dexter.withActivity(this)
                .withPermission(READ_CONTACTS)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        if (response.getPermissionName().equals(READ_CONTACTS)) {
                            addContact();

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
                }).check();
        loaddata();
    }
    ArrayList caigido;
    public void loaddata() {
        danhBaCLickInterfact = new DanhBaCLickInterfact() {
            @Override
            public void onItemClick(int position) {
                DialogSua();
            }
        };
        database = new Database(danhsach.this);
        contactList = new ArrayList<>();
        caigido = new ArrayList<>();
        Cursor cursor = database.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                caigido.add(cursor.getString(0));
                Contact contact = new Contact(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                contactList.add(contact);
            }
            contactAdapter = new ContactAdapter(this, contactList, danhBaCLickInterfact);
            recyclerView.setAdapter(contactAdapter);
            sort();
        }
    }

    String id1, name1, phone1, avatar1;
    private void addContact() {
        contactList = new ArrayList<>();
        database = new Database(danhsach.this);
        Cursor cursor2 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        contactList = new ArrayList<>();
        while (cursor2.moveToNext()) {
            id1 = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
            name1 = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phone1 = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            avatar1 = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
            Contact contact = new Contact(id1, name1, phone1, avatar1);
            database.addData(id1, name1, phone1, avatar1);
            contactList.add(contact);
        }
        contactAdapter = new ContactAdapter(this, contactList, danhBaCLickInterfact);

    }

    public void swiptorefresh() {
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                Toast.makeText(danhsach.this, "Refresh", Toast.LENGTH_SHORT).show();
                loaddata();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void sort() {
        Collections.sort(contactList);
        contactAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                contactAdapter.getFilter().filter(s);
                return false;
            }
        });
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
                Toast.makeText(this, "Sửa", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemthoat:
                DigalogThoat();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void DigalogThoat() {
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

    public void DialogSua() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit_phone);
        final EditText edtname, edtphone, edtavatar;
        Button btnaccept, btncancle;
        edtname = dialog.findViewById(R.id.editName_dialog);
        edtphone = dialog.findViewById(R.id.editPhone_dialog);
        edtavatar = dialog.findViewById(R.id.editAvatar_dialog);
        edtname.setText(caigido.toString());

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
                    Toast.makeText(danhsach.this, "Không được bỏ trống số điện thoại", Toast.LENGTH_SHORT).show();
                } else {
                   // database.UpdateData(edtname.getText().toString(), edtphone.getText().toString(), edtavatar.getText().toString(), idload);
                    Toast.makeText(danhsach.this, "Đã thay đổi thành công", Toast.LENGTH_SHORT).show();
                   loaddata();
                }

            }
        });
        dialog.show();
    }

    public void remove(int position) {
        contactList.remove(position);
        contactAdapter.notifyItemRemoved(position);
    }
    ItemTouchHelper.SimpleCallback itemtouchhelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            database = new Database(danhsach.this);
            Toast.makeText(danhsach.this, "Delete Success: ", Toast.LENGTH_SHORT).show();
            //database.DeleteData();
           // remove(viewHolder.getAdapterPosition());
        }
    };
    @Override
    public void onItemClick(int position) {

    }
}