package com.example.sqlite.operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sqlite.database.BooksDB;
import com.example.sqlite.database.Businesstable;
import com.example.sqlite.database.Expensestable;

public class Businessoperations {
    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    public Businessoperations(Context context){
        dbhandler = new BooksDB(context);
    }
    public void open(){
        database = dbhandler.getWritableDatabase();
    }
    public void close(){
        dbhandler.close();
    }
    public boolean addBiz(String pin,String bizname,String bizdesc){
        ContentValues values = new ContentValues();
        values.put(Businesstable.COLUMN_PIN,pin);
        values.put(Businesstable.COLUMN_NAME,bizname);
        values.put(Businesstable.COLUMN_DESCRIPTION,bizdesc);
        database.insert(Businesstable.TABLE_BUSINESSES,null,values);
        return true;
    }
    public boolean deleteBusiness(String id){
        database = dbhandler.getWritableDatabase();
        database.delete(Businesstable.TABLE_BUSINESSES,"id="+id,null);
        return true;
    }

    public boolean editBiz(String id,String bizname,String bizdesc){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Businesstable.COLUMN_NAME,bizname);
        contentValues.put(Businesstable.COLUMN_DESCRIPTION,bizdesc);
        database.update(Businesstable.TABLE_BUSINESSES,contentValues,"id="+id,null);
        return true;
    }

}
