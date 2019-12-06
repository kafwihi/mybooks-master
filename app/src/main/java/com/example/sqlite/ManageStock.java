package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sqlite.database.BooksDB;
import com.example.sqlite.helpers.Alerts;
import com.example.sqlite.operations.Stockoperations;

public class ManageStock extends AppCompatActivity {

    EditText itemname,itemdesc,itemcost;
    Button editbtn,deletebtn;
    Intent intent;
    String id;
    BooksDB booksDB;
    Alerts alerts;
    SQLiteDatabase database;
    String costtxt,itemtxt,itemdesctxt;
    Stockoperations stockoperations;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_stock);

        itemname = (EditText) findViewById(R.id.itemname);
        itemdesc = (EditText) findViewById(R.id.itemdesc);
        itemcost = (EditText) findViewById(R.id.cost);

        stockoperations = new Stockoperations(getApplicationContext());
        alerts = new Alerts(this);

        intent = getIntent();
        id = intent.getExtras().getString("KEY");
        getDetails(id);
        editbtn = (Button) findViewById(R.id.editbtn);
        deletebtn = (Button) findViewById(R.id.deletebtn);

        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemtxt = itemname.getText().toString();
                itemdesctxt = itemdesc.getText().toString();
                costtxt = itemcost.getText().toString();

                if(itemtxt.isEmpty()||itemdesctxt.isEmpty()||costtxt.isEmpty()){
                    String message = "Please ente all the fields";
                    alerts.showDialog(message);
                }
                else {
                    stockoperations.open();
                    stockoperations.updateStock(id,itemtxt,itemdesctxt,costtxt);
                    String message ="Stock updated successfully";
                    alerts.showDialog(message);
                    stockoperations.close();
                }

            }
        });
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stockoperations.open();
                stockoperations.deleteStock(id);
                String message = "Stock deleted successfully";
                alerts.showDialog(message);
                stockoperations.close();
            }
        });
    }
    private void getDetails(String id){
        booksDB = new BooksDB(getApplicationContext());
        database = booksDB.getReadableDatabase();
        Cursor res = database.rawQuery("Select item,description,cost from stock where id="+id,null);
        if(res != null)
            if(res.moveToFirst()) {
                costtxt = res.getString(res.getColumnIndex("cost"));
                itemtxt = res.getString(res.getColumnIndex("item"));
                itemdesctxt = res.getString(res.getColumnIndex("description"));

                itemcost.setText(costtxt);
                itemname.setText(itemtxt);
                itemdesc.setText(itemdesctxt);
            }
    }
}
