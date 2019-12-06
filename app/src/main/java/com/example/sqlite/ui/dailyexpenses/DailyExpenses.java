package com.example.sqlite.ui.dailyexpenses;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProviders;

import android.app.ActionBar;
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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.sqlite.Adapters.ExpensesAdapter;
import com.example.sqlite.ManageExpenses;
import com.example.sqlite.R;
import com.example.sqlite.database.BooksDB;
import com.example.sqlite.helpers.Alerts;
import com.example.sqlite.helpers.SessionManager;
import com.example.sqlite.objects.Expenses;
import com.example.sqlite.operations.Expenseoperations;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DailyExpenses extends Fragment {
    ListView dailyexpenses;
    public ArrayList<String> id = new ArrayList<String>();
    public ArrayList<String> expensetype = new ArrayList<String>();
    public ArrayList<String> expensedescription = new ArrayList<String>();
    public ArrayList<String> expensecost = new ArrayList<String>();

    SessionManager sessionManager;
    SharedPreferences sharedPreferences;
    Alerts alerts;
    Expenseoperations expenseoperations;
    String pin;
    BooksDB booksDB;
    SQLiteDatabase database;
    int dayofmonth;
    int month;
    int year;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.daily_expenses_fragment, container, false);
        dailyexpenses = root.findViewById(R.id.dailyexpenses);

        alerts = new Alerts(getActivity());
        expenseoperations = new Expenseoperations(getActivity());

        sessionManager = new SessionManager(getActivity());
        sharedPreferences = this.getActivity().getSharedPreferences("LoginPref", Context.MODE_PRIVATE);
        pin = sharedPreferences.getString("pin","");

//        LocalDate today = LocalDate.now();
//
//        day = today.getDayOfMonth();
//        Month month = today.getMonth();
//        year = today.getYear();

        Calendar calendar = Calendar.getInstance();
        dayofmonth = calendar.get(Calendar.DATE);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        String date = dayofmonth+"/"+month+"/"+year;
        buildTable(pin,date);

        return root;
    }
    private void buildTable(String pin, String date){
        booksDB = new BooksDB(getActivity());
        database = booksDB.getReadableDatabase();
        final Cursor res = database.rawQuery("Select id,cost,expensetype,expensedescription from expenses where pin="+pin+" AND date='"+date+"'"+" ORDER BY id DESC",null);
        if(res.moveToFirst()){
            do{
                //id.add(res.getString(res.getColumnIndex("id")));
                expensetype.add(res.getString(res.getColumnIndex("expensetype")));
                expensecost.add(res.getString(res.getColumnIndex("cost")));
                expensedescription.add(res.getString(res.getColumnIndex("expensedescription")));
            }
            while (res.moveToNext());
        }
        ExpensesAdapter expensesAdapter = new ExpensesAdapter(getActivity(),expensetype,expensedescription,expensecost);
        dailyexpenses.setAdapter(expensesAdapter);
        dailyexpenses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ManageExpenses.class);
                if(res.moveToFirst()){
                    res.moveToPosition(position);
                    intent.putExtra("KEY",res.getString(0));
                    startActivity(intent);
                    //res.close();
                }
            }
        });
        //res.close();
    }

}
