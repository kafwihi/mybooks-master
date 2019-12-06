package com.example.sqlite.operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sqlite.database.BooksDB;
import com.example.sqlite.database.Stocktable;

public class Stockoperations {
    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    public Stockoperations(Context context){
        dbhandler = new BooksDB(context);
    }

    public void open(){
        database = dbhandler.getWritableDatabase();
    }

    public void close(){
        dbhandler.close();
    }

    public boolean addStock(String pin, String item, String description, String cost, String date, int year, int month){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Stocktable.COLUMN_PIN,pin);
        contentValues.put(Stocktable.COLUMN_ITEM,item);
        contentValues.put(Stocktable.COLUMN_ITEM_DESCRIPTION,description);
        contentValues.put(Stocktable.COLUMN_PRICE,cost);
        contentValues.put(Stocktable.COLUMN_DAY,date);
        contentValues.put(Stocktable.COLUMN_YEAR,year);
        contentValues.put(Stocktable.COLUMN_MONTH,month);
        database.insert(Stocktable.TABLE_NAME,null,contentValues);
        return true;
    }
    public boolean deleteStock(String id){
        database = dbhandler.getWritableDatabase();
        database.delete(Stocktable.TABLE_NAME,"id="+id,null);
        return true;
    }
    public boolean updateStock(String id,String itemname,String itemdescription,String cost){
        ContentValues values = new ContentValues();
        values.put(Stocktable.COLUMN_ITEM,itemname);
        values.put(Stocktable.COLUMN_ITEM_DESCRIPTION,itemdescription);
        values.put(Stocktable.COLUMN_PRICE,cost);
        database.update(Stocktable.TABLE_NAME,values,"id="+id,null);
        return true;
    }
    public String getDailyStock(String pin,String date){
        database = dbhandler.getReadableDatabase();
        Cursor res = database.rawQuery("Select SUM(cost) as totalstock from stock where pin="+pin+" AND " +
                "date='"+date+"'",null);
        if(res!=null)
            res.moveToFirst();
        String totalstock = res.getString(res.getColumnIndex("totalstock"));
        return totalstock;
    }
    public String getMonthlyStock(String pin, int month, int year){
        database = dbhandler.getReadableDatabase();
        Cursor res = database.rawQuery("Select SUM(cost) as totalstock from stock where pin="+pin+" AND " +
                "month='"+month+"'"+" AND year="+year,null);
        if(res!=null)
            res.moveToFirst();
        String totalstock = res.getString(res.getColumnIndex("totalstock"));
        return totalstock;
    }

}
