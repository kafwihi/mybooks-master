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
import com.example.sqlite.operations.Salesoperations;

public class ManageSales extends AppCompatActivity {
    EditText sellingprice,itemname,itemdesc;
    Button  editbtn,deletbtn;
    Intent intent;
    String id;
    BooksDB booksDB;
    Alerts alerts;
    SQLiteDatabase database;
    Salesoperations salesoperations;
    String pricetxt,itemtxt,desctxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_sales);

        sellingprice = (EditText) findViewById(R.id.sellingprice);
        itemname = (EditText) findViewById(R.id.itemname);
        itemdesc = (EditText) findViewById(R.id.itemdesc);

        alerts = new Alerts(this);

        salesoperations = new Salesoperations(getApplicationContext());

        intent = getIntent();
        id = getIntent().getExtras().getString("KEY");
        getDetails(id);

        editbtn = (Button) findViewById(R.id.editbtn);
        deletbtn = (Button) findViewById(R.id.deletebtn);

        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String itemnametxt = itemname.getText().toString();
               String itemdesctxt = itemdesc.getText().toString();
               String sellingpricetxt = sellingprice.getText().toString();

               if(itemnametxt.isEmpty()||itemdesctxt.isEmpty()||sellingpricetxt.isEmpty()){
                   String message = "Enter all details please";
                   alerts.showDialog(message);
               }
               else {
                   salesoperations.open();
                   salesoperations.updateSale(id,itemnametxt,itemdesctxt,sellingpricetxt);
                   String message ="Sale updated successfully";
                   alerts.showDialog(message);
                   salesoperations.close();
               }

            }
        });
        deletbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salesoperations.open();
                salesoperations.deleteSale(id);
                String message = "Sale deleted successfully";
                alerts.showDialog(message);
                salesoperations.close();
            }
        });
    }
    private void getDetails(String id){
        booksDB = new BooksDB(getApplicationContext());
        database = booksDB.getReadableDatabase();
        Cursor res = database.rawQuery("Select selling_price,item,description from sales where id="+id,null);
        if(res != null)
            if(res.moveToFirst()) {
                pricetxt = res.getString(res.getColumnIndex("selling_price"));
                itemtxt = res.getString(res.getColumnIndex("item"));
                desctxt = res.getString(res.getColumnIndex("description"));

                sellingprice.setText(pricetxt);
                itemname.setText(itemtxt);
                itemdesc.setText(desctxt);
            }
    }
}
