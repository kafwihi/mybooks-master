package com.example.sqlite.ui.dailysalesreports;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sqlite.Adapters.ExpensesAdapter;
import com.example.sqlite.Adapters.SalesAdapter;
import com.example.sqlite.ManageSales;
import com.example.sqlite.R;
import com.example.sqlite.database.BooksDB;
import com.example.sqlite.helpers.Alerts;
import com.example.sqlite.helpers.SessionManager;
import com.example.sqlite.operations.Expenseoperations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class DailySalesReports extends Fragment {
    ListView dailysales;
    public ArrayList<String> id = new ArrayList<String>();
    public ArrayList<String> item = new ArrayList<String>();
    public ArrayList<String> description = new ArrayList<String>();
    public ArrayList<String> cost = new ArrayList<String>();

    SessionManager sessionManager;
    SharedPreferences sharedPreferences;
    Alerts alerts;
    String pin;
    BooksDB booksDB;
    SQLiteDatabase database;
    int dayofmonth,year,month;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_todaysales, container, false);
        dailysales = root.findViewById(R.id.dailysales);

        sessionManager = new SessionManager(getActivity());
        sharedPreferences = this.getActivity().getSharedPreferences("LoginPref", Context.MODE_PRIVATE);
        pin = sharedPreferences.getString("pin","");

        Calendar calendar = Calendar.getInstance();
        dayofmonth = calendar.get(Calendar.DATE);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        String date = dayofmonth+"/"+month+"/"+year;

        getDailyReports(pin,date);

        return root;
    }
    private void getDailyReports(String pin,String date){
        booksDB = new BooksDB(getActivity());
        database = booksDB.getReadableDatabase();
        alerts = new Alerts(getActivity());
        final Cursor res = database.rawQuery("Select id,item,description,selling_price from " +
                "sales where pin="+pin+" AND date='"+date+"'"+" ORDER BY id DESC",null);

        if(res.moveToFirst()){
            do{
                //id.add(res.getString(res.getColumnIndex("id")));
                item.add(res.getString(res.getColumnIndex("item")));
                cost.add(res.getString(res.getColumnIndex("selling_price")));
                description.add(res.getString(res.getColumnIndex("description")));
            }
            while (res.moveToNext());
        }
        SalesAdapter salesAdapter = new SalesAdapter(getActivity(),item,description,cost);
        dailysales.setAdapter(salesAdapter);
        dailysales.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ManageSales.class);
                if(res.moveToFirst()){
                    res.moveToPosition(position);
                    intent.putExtra("KEY",res.getString(0));
                    startActivity(intent);
                    //res.close();
                }
            }
        });
       // res.close();
    }
}