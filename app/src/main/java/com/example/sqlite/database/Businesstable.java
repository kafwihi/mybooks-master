package com.example.sqlite.database;

public class Businesstable {
    //TABLE
    public static final String TABLE_BUSINESSES= "businesses";
    //pin
    public static final String COLUMN_PIN= "pin";
    //bizname
    public static final String COLUMN_NAME= "business_name";

    //bzdesc
    public static final String COLUMN_DESCRIPTION= "business_description";

    //BIZID
    public static final String COLUMN_ID= "id";
    //BUSINESSTABLE
    public static final String CREATE_TABLE_BUSINESSES = "CREATE TABLE "+TABLE_BUSINESSES+" ("+
            COLUMN_PIN+" INTEGER"+","+
            COLUMN_NAME+ " text"+","+COLUMN_DESCRIPTION+" text"+","+COLUMN_ID+ " integer primary key"+","+
            "FOREIGN KEY(pin) REFERENCES users(pin)"+")";


}
