package com.example.carlos.agenda.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.carlos.agenda.models.Contact;

import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String appDatabase = "agenda";
    public static final int version = 1;

    public DatabaseHelper(Context context){
        super(context, appDatabase, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE contacts (_id integer primary key autoincrement, " +
                "name TEXT, " +
                "phone_number TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS contacts;";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insertContent(String name, String number){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues valuesToInsert = new ContentValues();
        valuesToInsert.put("name", name);
        valuesToInsert.put("phone_number", number);
        database.insert("contacts", null, valuesToInsert);
        database.close();
    }

    public void update(String id, String name, String number){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues valuesToInsert = new ContentValues();
        if(!name.equals("")) valuesToInsert.put("name", name);
        if(!number.equals("")) valuesToInsert.put("phone_number", number);
        database.update("contacts", valuesToInsert, "_id=?", new String[]{id});
        database.close();
    }

    public List<Contact> selectAll(){
        List<Contact> listContacts = null;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query("contacts",
                new String[] {"_id", "name", "phone_number"},
                null, null, null, null, null);
        if(cursor.moveToFirst()){
            do {
                String id = cursor.getString(cursor.getColumnIndex("_id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String number = cursor.getString(cursor.getColumnIndex("phone_number"));
                listContacts.add(new Contact(id, name, number));
            }while(cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return listContacts;
    }

    public void delete(String id){
        SQLiteDatabase database = this.getReadableDatabase();
        database.delete("contacts", "_id=?", new String[]{id});
        database.close();
    }
}
