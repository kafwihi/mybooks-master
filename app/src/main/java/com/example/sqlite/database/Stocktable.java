package com.example.sqlite.database;

public class Stocktable {
    public static String TABLE_NAME = "stock";

    public static String COLUMN_ITEM = "item";

    public static String COLUMN_PIN = "pin";

    public static String COLUMN_ID = "id";

    public static String COLUMN_ITEM_DESCRIPTION = "description";

    public static String COLUMN_PRICE = "cost";

    public static String COLUMN_DAY = "date";

    public static String COLUMN_YEAR = "year";

    public static String COLUMN_MONTH = "month";

    public static String CREATE_TABLE_STOCK = "CREATE TABLE "+TABLE_NAME+"("+COLUMN_ITEM+" text"+","+
            COLUMN_PIN+ " INTEGER"+","+COLUMN_ID+" integer primary key"+","+
            COLUMN_ITEM_DESCRIPTION+" text"+","+COLUMN_PRICE+" double"+","+COLUMN_DAY+" text"+","+COLUMN_YEAR+" INTEGER"+","+COLUMN_MONTH+" text"+","+"FOREIGN KEY("+COLUMN_PIN+")"+" REFERENCES "+Userstable.TABLE_USERS+"("+Userstable.COLUMN_PIN+")"+")";

}
