package com.example.sqlite.database;

public class Expensestable {
    public static String TABLE_NAME = "expenses";

    public static String COLUMN_PIN = "pin";

    public static String COLUMN_EXPENSETYPE ="expensetype";

    public static String COLUMN_EXPENSEAMOUNT = "cost";

    public static String COLUMN_EXPENSEDESCRIPTION = "expensedescription";

    public static String COLUMN_DAY ="date";

    public static String COLUMN_BIZID ="business_id";

    public static String COLUMN_ID = "id";

    public static String COLUMN_YEAR = "year";

    public static String COLUMN_MONTH = "month";

    public static String CREATE_TABLE_EXPENSES = "CREATE TABLE "+TABLE_NAME+ "("+COLUMN_PIN+ " INTEGER"+","+
            COLUMN_EXPENSETYPE+ " text"+","+COLUMN_EXPENSEAMOUNT+" double"+","+COLUMN_EXPENSEDESCRIPTION+" text"+
            ","+COLUMN_DAY+" text"+","+COLUMN_YEAR+" INTEGER"+","+COLUMN_MONTH+" text"+","+COLUMN_ID+" integer primary key"+","+"FOREIGN KEY("+COLUMN_PIN+")"+" REFERENCES " +
            Userstable.TABLE_USERS+"("+Userstable.COLUMN_PIN+")"+")";


}
