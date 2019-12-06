package com.example.sqlite.ui.allstock;

import androidx.annotation.RequiresApi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.sqlite.Adapters.AllExpensesAdapter;
import com.example.sqlite.Adapters.AllStockAdapter;
import com.example.sqlite.ManageStock;
import com.example.sqlite.R;
import com.example.sqlite.database.BooksDB;
import com.example.sqlite.helpers.Alerts;
import com.example.sqlite.helpers.SessionManager;
import com.example.sqlite.operations.Expenseoperations;

import java.time.LocalDate;
import java.util.ArrayList;

public class AllStock extends Fragment {
    ListView allstock;
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
        View root = inflater.inflate(R.layout.all_stock_fragment, container, false);
        allstock = root.findViewById(R.id.allstock);

        sessionManager = new SessionManager(getActivity());
        sharedPreferences = this.getActivity().getSharedPreferences("LoginPref", Context.MODE_PRIVATE);
        pin = sharedPreferences.getString("pin","");

        getAllStock(pin);
        return root;
    }
    private void getAllStock(String pin){
        booksDB = new BooksDB(getActivity());
        database = booksDB.getReadableDatabase();
        alerts = new Alerts(getActivity());
        final Cursor res = database.rawQuery("Select id,item,cost,date,description from stock where " +
                "pin="+pin+" ORDER BY id DESC",null);
        if(res == null){
            String message = "No records found";
            alerts.showDialog(message);
        }
        if(res.moveToFirst()){
            do{
                //id.add(res.getString(res.getColumnIndex("id")));
                date.add(res.getString(res.getColumnIndex("date")));
                item.add(res.getString(res.getColumnIndex("item")));
                cost.add(res.getString(res.getColumnIndex("cost")));
                description.add(res.getString(res.getColumnIndex("description")));
            }
            while(res.moveToNext());
        }
        AllStockAdapter allStockAdapter = new AllStockAdapter(getActivity(),date,item,description,cost);
        allstock.setAdapter(allStockAdapter);
        allstock.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ManageStock.class);
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
