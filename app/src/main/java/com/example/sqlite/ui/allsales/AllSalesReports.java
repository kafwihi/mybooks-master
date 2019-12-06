package com.example.sqlite.ui.allsales;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.sqlite.Adapters.AllExpensesAdapter;
import com.example.sqlite.Adapters.AllSalesAdapter;
import com.example.sqlite.ManageSales;
import com.example.sqlite.R;
import com.example.sqlite.database.BooksDB;
import com.example.sqlite.helpers.Alerts;
import com.example.sqlite.helpers.SessionManager;

import java.util.ArrayList;

public class AllSalesReports extends Fragment {
    ListView allsales;
    public ArrayList<String> id = new ArrayList<String>();
    public ArrayList<String> date = new ArrayList<String>();
    public ArrayList<String> item = new ArrayList<String>();
    public ArrayList<String> description = new ArrayList<String>();
    public ArrayList<String> cost = new ArrayList<String>();

    SessionManager sessionManager;
    SharedPreferences sharedPreferences;
    String pin;
    BooksDB booksDB;
    SQLiteDatabase database;
    Alerts alerts;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.all_sales_reports_fragment, container, false);

        allsales = root.findViewById(R.id.allsales);
        sessionManager = new SessionManager(getActivity());
        sharedPreferences = this.getActivity().getSharedPreferences("LoginPref", Context.MODE_PRIVATE);
        pin = sharedPreferences.getString("pin","");
        displayExpenses(pin);

        return root;
    }
    private void displayExpenses(String pin){
        booksDB = new BooksDB(getActivity());
        alerts = new Alerts(getActivity());
        database = booksDB.getReadableDatabase();
        final Cursor res = database.rawQuery("Select id,date,item,description,selling_price from sales where pin="+pin+" ORDER BY id desc",null);
        if(res == null){
            String message = "No records found";
            alerts.showDialog(message);
        }
        if(res.moveToFirst()){
            do{
                //id.add(res.getString(res.getColumnIndex("id")));
                date.add(res.getString(res.getColumnIndex("date")));
                item.add(res.getString(res.getColumnIndex("item")));
                description.add(res.getString(res.getColumnIndex("description")));
                cost.add(res.getString(res.getColumnIndex("selling_price")));
            }
            while(res.moveToNext());
            AllSalesAdapter allSalesAdapter = new AllSalesAdapter(getActivity(),date,item,description,cost);
            allsales.setAdapter(allSalesAdapter);
            allsales.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), ManageSales.class);
                    if(res.moveToFirst()){
                        res.moveToPosition(position);
                        intent.putExtra("KEY", res.getString(0));
                        startActivity(intent);
                        //res.close();
                    }
                }
            });
            //res.close();
        }
    }
}
