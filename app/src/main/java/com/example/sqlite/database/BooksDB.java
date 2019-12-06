package com.example.sqlite.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.example.sqlite.database.Userstable.CREATE_TABLE_USERS;

public class BooksDB extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "books";
    public static final int DATABASE_VERSION = 1;

    public BooksDB(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Userstable.CREATE_TABLE_USERS);
        db.execSQL(Businesstable.CREATE_TABLE_BUSINESSES);
        db.execSQL(Expensestable.CREATE_TABLE_EXPENSES);
        db.execSQL(Salestable.CREATE_TABLE_SALES);
        db.execSQL(Stocktable.CREATE_TABLE_STOCK);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL(Userstable.CREATE_TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS businesses");
        db.execSQL(Businesstable.CREATE_TABLE_BUSINESSES);
        db.execSQL("DROP TABLE IF EXISTS expenses");
        db.execSQL(Expensestable.CREATE_TABLE_EXPENSES);
        db.execSQL("DROP TABLE IF EXISTS expenses");
        db.execSQL(Salestable.CREATE_TABLE_SALES);
        db.execSQL("DROP TABLE IF EXISTS sales");
        db.execSQL(Stocktable.CREATE_TABLE_STOCK);
    }
}
