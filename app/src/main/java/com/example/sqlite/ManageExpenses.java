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
import com.example.sqlite.operations.Expenseoperations;

public class ManageExpenses extends AppCompatActivity {
    EditText expensetype,cost,description;
    Button edit,delete;
    Intent intent;
    String id;
    BooksDB booksDB;
    Alerts alerts;
    SQLiteDatabase database;
    String costtxt,typetxt,desctxt;
    Expenseoperations expenseoperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_expenses);
        expensetype = (EditText) findViewById(R.id.expensetype);
        cost = (EditText) findViewById(R.id.cost);
        expenseoperations = new Expenseoperations(getApplicationContext());
        description = (EditText) findViewById(R.id.description);
        edit = (Button) findViewById(R.id.editexpense);
        delete = (Button) findViewById(R.id.deletebtn);
        alerts = new Alerts(this);

        intent = getIntent();
        id = intent.getExtras().getString("KEY");
        Log.d("TAG","ID IS "+id);
        getDetails(id);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String costtf = cost.getText().toString();
                String descriptiontf = description.getText().toString();
                String typetf = expensetype.getText().toString();
                if(costtf.isEmpty()||descriptiontf.isEmpty()||typetf.isEmpty()){
                    String message = "Enter all fields";
                    alerts.showDialog(message);
                }
                else {
                    expenseoperations.open();
                    expenseoperations.updateExpense(id,typetf,descriptiontf,costtf);
                    String message = "Expense Updated successfully";
                    alerts.showDialog(message);
                    expenseoperations.close();
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expenseoperations.open();
                expenseoperations.deleteExpense(id);
                String message = "Expense deleted successfully";
                alerts.showDialog(message);
                expenseoperations.close();
            }
        });
    }
    private void getDetails(String id){
        booksDB = new BooksDB(getApplicationContext());
        database = booksDB.getReadableDatabase();
        Cursor res = database.rawQuery("Select expensetype,cost,expensedescription from expenses where id="+id,null);
        if(res != null)
        if(res.moveToFirst()) {
            costtxt = res.getString(res.getColumnIndex("cost"));
            typetxt = res.getString(res.getColumnIndex("expensetype"));
            desctxt = res.getString(res.getColumnIndex("expensedescription"));

            cost.setText(costtxt);
            expensetype.setText(typetxt);
            description.setText(desctxt);
        }
    }

}
