package com.example.sqlite.operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sqlite.database.BooksDB;
import com.example.sqlite.database.Expensestable;

public class Expenseoperations {
    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    public Expenseoperations(Context context){
        dbhandler = new BooksDB(context);
    }

    public void open(){
        database = dbhandler.getWritableDatabase();
    }

    public void close(){
        dbhandler.close();
    }

    public boolean addexpense(String pin, String expensetype, String expensedesc, String cost, String date,
                              int month, int year){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Expensestable.COLUMN_PIN,pin);
        contentValues.put(Expensestable.COLUMN_EXPENSETYPE,expensetype);
        contentValues.put(Expensestable.COLUMN_EXPENSEDESCRIPTION,expensedesc);
        contentValues.put(Expensestable.COLUMN_EXPENSEAMOUNT,cost);
        contentValues.put(Expensestable.COLUMN_DAY,date);
        contentValues.put(Expensestable.COLUMN_YEAR,year);
        contentValues.put(Expensestable.COLUMN_MONTH,month);
        database.insert(Expensestable.TABLE_NAME,null,contentValues);
        return true;
    }
    public Cursor getDailyExpenses(String pin){
        database = dbhandler.getReadableDatabase();
        Cursor res = database.rawQuery("Select id,expensetype,cost,expensedescription from expenses where pin="+pin,null);
        return res;
    }
    public boolean deleteExpense(String id){
        database = dbhandler.getWritableDatabase();
        database.delete(Expensestable.TABLE_NAME,"id="+id,null);
        return true;
    }
    public boolean updateExpense(String id,String expensetype,String expensedescription,String expensecost){
        ContentValues values = new ContentValues();
        values.put(Expensestable.COLUMN_EXPENSETYPE,expensetype);
        values.put(Expensestable.COLUMN_EXPENSEAMOUNT,expensecost);
        values.put(Expensestable.COLUMN_EXPENSEDESCRIPTION,expensedescription);
        database.update(Expensestable.TABLE_NAME,values,"id="+id,null);
        return true;
    }
    public String getDailyExpenses(String pin,String date){
        database = dbhandler.getReadableDatabase();
        Cursor res = database.rawQuery("Select SUM(cost) as dailyexpenses from expenses where pin="+pin+" AND " +
                "date='"+date+"'",null);
        if(res!=null)
            res.moveToFirst();
        String dailyexpenses = res.getString(res.getColumnIndex("dailyexpenses"));
        return dailyexpenses;
    }
    public String getMonthlyExpenses(String pin,int month,int year){
        database = dbhandler.getReadableDatabase();
        Cursor res = database.rawQuery("Select SUM(cost) as monthlyexpenses from expenses where pin="+pin+" AND " +
                "month='"+month+"'"+" AND year="+year,null);
        if(res!=null)
            res.moveToFirst();
        String monthlyexpenses = res.getString(res.getColumnIndex("monthlyexpenses"));
        return monthlyexpenses;
    }
}
