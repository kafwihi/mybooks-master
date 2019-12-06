package com.example.sqlite.operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sqlite.database.BooksDB;
import com.example.sqlite.database.Expensestable;
import com.example.sqlite.database.Salestable;

public class Salesoperations {
    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    public Salesoperations(Context context){
        dbhandler = new BooksDB(context);
    }
    public void open(){
        database = dbhandler.getWritableDatabase();
    }
    public void close(){
        dbhandler.close();
    }
    public boolean addSales(String pin, String item, String itemdescription, String sellingprice, String date, int year, int month){
        ContentValues values = new ContentValues();
        values.put(Salestable.COLUMN_PIN,pin);
        values.put(Salestable.COLUMN_ITEM,item);
        values.put(Salestable.COLUMN_ITEM_DESCRIPTION,itemdescription);
        values.put(Salestable.COLUMN_SELLING_PRICE,sellingprice);
        values.put(Salestable.COLUMN_DAY,date);
        values.put(Salestable.COLUMN_YEAR,year);
        values.put(Salestable.COLUMN_MONTH,month);

        database.insert(Salestable.TABLE_NAME,null,values);
        return true;
    }

    public boolean deleteSale(String id){
        database = dbhandler.getWritableDatabase();
        database.delete(Salestable.TABLE_NAME,"id="+id,null);
        return true;
    }
    public boolean updateSale(String id,String itemname,String itemdescription,String sellingprice){
        ContentValues values = new ContentValues();
        values.put(Salestable.COLUMN_ITEM,itemname);
        values.put(Salestable.COLUMN_ITEM_DESCRIPTION,itemdescription);
        values.put(Salestable.COLUMN_SELLING_PRICE,sellingprice);
        database.update(Salestable.TABLE_NAME,values,"id="+id,null);
        return true;
    }
    public String getDailySales(String pin,String date){
        database = dbhandler.getReadableDatabase();
        Cursor res = database.rawQuery("Select SUM(selling_price) as totalsales from sales where pin="+pin+" AND " +
                "date='"+date+"'",null);
        if(res!=null)
            res.moveToFirst();
        String totalsales = res.getString(res.getColumnIndex("totalsales"));
        return totalsales;
    }
    public String getMonthlySales(String pin,int month,int year){
        database = dbhandler.getReadableDatabase();
        Cursor res = database.rawQuery("Select SUM(selling_price) as monthlysales from sales where pin="+pin+" AND " +
                "month='"+month+"'"+" AND year="+year,null);
        if(res!=null)
            res.moveToFirst();
        String monthlysales = res.getString(res.getColumnIndex("monthlysales"));
        return monthlysales;
    }

}
