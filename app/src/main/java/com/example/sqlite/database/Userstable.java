package com.example.sqlite.database;

public class Userstable {
    //users table
    public static final String TABLE_USERS= "users";
    //columns
    public static final String COLUMN_FIRSTNAME= "firstname";
    public static final String COLUMN_LASTNAME= "lastname";
    public static final String COLUMN_PIN= "pin";

    public static final String CREATE_TABLE_USERS = "CREATE TABLE "+TABLE_USERS+" ("+COLUMN_PIN+ " INTEGER"+","+
            COLUMN_FIRSTNAME+" text"+","+
            COLUMN_LASTNAME+ " text"+","+"primary key(pin)"+")";


}
