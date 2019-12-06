package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sqlite.database.BooksDB;
import com.example.sqlite.helpers.Alerts;
import com.example.sqlite.operations.Businessoperations;
import com.example.sqlite.operations.Expenseoperations;

public class ManageBusiness extends AppCompatActivity {
    EditText bizname,bizdesc;
    Button edit,delete;
    Intent intent;
    String id;
    BooksDB booksDB;
    Alerts alerts;
    SQLiteDatabase database;
    String biznametxt,bizdesctxt,biznametf,bizdesctf;
    Businessoperations businessoperations;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_business);
        bizname = (EditText) findViewById(R.id.bizname);
        alerts = new Alerts(getApplicationContext());
        bizdesc = (EditText) findViewById(R.id.bizdesc);
        edit = (Button) findViewById(R.id.editbiz);
        delete = (Button) findViewById(R.id.deletebtn);
        alerts = new Alerts(this);

        businessoperations = new Businessoperations(getApplicationContext());

        intent = getIntent();
        id = intent.getExtras().getString("KEY");

        Log.d("TAG","id is "+id);

        getDetails(id);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biznametf = bizname.getText().toString();
                bizdesctf = bizdesc.getText().toString();

                if(biznametf.isEmpty()||bizdesctf.isEmpty()){
                    String message = "Please enter all fields";
                    alerts.showDialog(message);
                }
                else{
                   businessoperations.open();
                   businessoperations.editBiz(id,biznametf,bizdesctf);
                   String message = "Business Updated successfully";
                   alerts.showDialog(message);
                   businessoperations.close();
                }

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                businessoperations.open();
                businessoperations.deleteBusiness(id);
                String message = "Expense deleted successfully";
                alerts.showDialog(message);
                businessoperations.close();
            }
        });
    }
    private void getDetails(String id){
        booksDB = new BooksDB(getApplicationContext());
        database = booksDB.getReadableDatabase();
        Cursor res = database.rawQuery("Select business_name,business_description from businesses where id="+id,null);
        if(res != null)
            if(res.moveToFirst()) {
                biznametxt = res.getString(res.getColumnIndex("business_name"));
                bizdesctxt = res.getString(res.getColumnIndex("business_description"));
                Log.d("TAG","id is "+id);
                bizname.setText(biznametxt);
                bizdesc.setText(bizdesctxt);
                //description.setText(desctxt);
            }
    }
}
