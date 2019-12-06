package com.example.sqlite.operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sqlite.database.BooksDB;
import com.example.sqlite.database.Userstable;
import com.example.sqlite.objects.Users;

public class Useroperations {
    SQLiteDatabase database,db;
    SQLiteOpenHelper dbhandler;
    public Useroperations(Context context){
        dbhandler = new BooksDB(context);
    }
    public void open(){
        database = dbhandler.getWritableDatabase();
    }
    public void close(){
        dbhandler.close();
    }
    public boolean addusers(String firstnametxt,String lastnametxt,String pintxt){
        if(userlogin(pintxt)){
            return false;
        }
        else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("firstname", firstnametxt);
            contentValues.put("lastname", lastnametxt);
            contentValues.put("pin", pintxt);
            database.insert(Userstable.TABLE_USERS, null, contentValues);
            return true;
        }
    }
    public Boolean userlogin(String pin){
        db = dbhandler.getReadableDatabase();
        Cursor res = db.rawQuery("Select * from users where pin='"+pin+"'",null);
        int rescount = res.getCount();
        res.close();
        if(rescount>0){
            return true;
        }
        else {
            return false;
        }
    }
    public String getDetails(String pin){
        db = dbhandler.getReadableDatabase();
        Cursor res = db.rawQuery("Select firstname,lastname from users where pin="+pin,null);
        if(res != null)
            res.moveToFirst();
        String firstname = res.getString(res.getColumnIndex("firstname"));
        String lastname = res.getString(res.getColumnIndex("lastname"));
        String fullname = firstname + " "+ lastname;
        return  fullname;
    }
}
