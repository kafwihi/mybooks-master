package com.example.sqlite.ui.allexpenses;

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
import com.example.sqlite.ManageExpenses;
import com.example.sqlite.R;
import com.example.sqlite.database.BooksDB;
import com.example.sqlite.helpers.Alerts;
import com.example.sqlite.helpers.SessionManager;
import com.example.sqlite.operations.Expenseoperations;

import java.util.ArrayList;

public class AllExpenses extends Fragment {
    ListView allexpenses;
    public ArrayList<String> expenseid= new ArrayList<String>();
    public ArrayList<String> date = new ArrayList<String>();
    public ArrayList<String> expensetype = new ArrayList<String>();
    public ArrayList<String> expensedescription = new ArrayList<String>();
    public ArrayList<String> expensecost = new ArrayList<String>();
    Expenseoperations expenseoperations;

    SessionManager sessionManager;
    SharedPreferences sharedPreferences;
    Alerts alerts;
    String pin;
    BooksDB booksDB;
    SQLiteDatabase database;
    public static AllExpenses newInstance() {
        return new AllExpenses();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.all_expenses_fragment, container, false);
        allexpenses = root.findViewById(R.id.allexpenses);
        expenseoperations = new Expenseoperations(getActivity());

        sessionManager = new SessionManager(getActivity());
        sharedPreferences = this.getActivity().getSharedPreferences("LoginPref", Context.MODE_PRIVATE);
        pin = sharedPreferences.getString("pin","");
        getAllExpenses(pin);

        return root;
    }
    private void getAllExpenses(String pin){
        alerts = new Alerts(getActivity());
        booksDB = new BooksDB(getActivity());
        database = booksDB.getReadableDatabase();
        final Cursor res = database.rawQuery("Select id,date,cost,expensetype,expensedescription from expenses where pin="+pin+" ORDER BY id desc",null);
        if(res ==null){
            String message = "No records found";
            alerts.showDialog(message);
        }
        if(res.moveToFirst()){
            do{
                //expenseid.add(res.getString(res.getColumnIndex("id")));
                date.add(res.getString(res.getColumnIndex("date")));
                expensecost.add(res.getString(res.getColumnIndex("cost")));
                expensetype.add(res.getString(res.getColumnIndex("expensetype")));
                expensedescription.add(res.getString(res.getColumnIndex("expensedescription")));
            }
            while(res.moveToNext());
            final AllExpensesAdapter allExpensesAdapter = new AllExpensesAdapter(getActivity(),
                    date,expensetype,expensecost,expensedescription);
            allexpenses.setAdapter(allExpensesAdapter);
            allexpenses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), ManageExpenses.class);
                    if(res.moveToFirst()) {
                        res.moveToPosition(position);
                        intent.putExtra("KEY", res.getString(0));
                        startActivity(intent);
                        //res.close();
                    }
                }
            });
        }
    }
}
