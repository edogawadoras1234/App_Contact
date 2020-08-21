package com.example.testdanhba;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class Database extends SQLiteOpenHelper {

    private Context context;
    private static final  String DATABASE_NAME = "MyContacts.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "DanhBa";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_PHONE = "Phone";
    private static final String COLUMN_AVATAR = "Avatar";
    public Database(@Nullable Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context = context;

    }


    public Integer DeleteData(String phone){
        SQLiteDatabase database = this.getWritableDatabase();
        return database.delete(TABLE_NAME,"Phone =?",new String[]{phone});
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_AVATAR + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addData(String name, String phone, String avatar){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_PHONE, phone);
        cv.put(COLUMN_AVATAR, avatar);
        long  result = db.insert(TABLE_NAME, null, cv);
    }
    Cursor finddata(String name){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE Name ='" + name + "'";
        Cursor cursor = null;
        if (database != null){
            cursor = database.rawQuery(query, null);
        }
        return cursor;

    }
    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

}
