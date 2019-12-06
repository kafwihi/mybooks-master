package com.example.sqlite.ui.searchreports;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sqlite.R;
import com.example.sqlite.helpers.Monthsclass;
import com.example.sqlite.helpers.SessionManager;
import com.example.sqlite.operations.Expenseoperations;
import com.example.sqlite.operations.Salesoperations;
import com.example.sqlite.operations.Stockoperations;

public class SearchReports extends Fragment {
    TextView date,monthlysales,monthlyexpenses,monthlystock,salesanalysis,outcome;
    Button search;
    Expenseoperations expenseoperations;
    Salesoperations salesoperations;
    Spinner monthofyear,yearpicked;
    Stockoperations stockoperations;
    private ArrayAdapter<CharSequence> monthadapter,yearadapter;

    SessionManager sessionManager;
    SharedPreferences sharedPreferences;
    String salestxt,stocktxt,expensetxt,profittxt,advice,month,yeartxt;
    int sales,stock,expense,profit,year,monthint;
    String pin;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.search_reports_fragment, container, false);
        date = root.findViewById(R.id.date);
        monthlysales = root.findViewById(R.id.monthlysales);
        monthlyexpenses = root.findViewById(R.id.monthlyexpenses);
        monthlystock = root.findViewById(R.id.monthlystock);
        salesanalysis = root.findViewById(R.id.salesanalysis);
        outcome = root.findViewById(R.id.outcome);
        search = root.findViewById(R.id.search);

        monthadapter = ArrayAdapter.createFromResource(getActivity(),R.array.months,
                android.R.layout.simple_spinner_item);
        monthadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        yearadapter = ArrayAdapter.createFromResource(getActivity(),R.array.years,
                android.R.layout.simple_spinner_item);
        yearadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);



        monthofyear = root.findViewById(R.id.month);
        yearpicked = root.findViewById(R.id.year);

        monthofyear.setAdapter(monthadapter);
        yearpicked.setAdapter(yearadapter);

        salesoperations = new Salesoperations(getActivity());
        stockoperations = new Stockoperations(getActivity());
        expenseoperations = new Expenseoperations(getActivity());

        sessionManager = new SessionManager(getActivity());
        sharedPreferences = this.getActivity().getSharedPreferences("LoginPref", Context.MODE_PRIVATE);
        pin = sharedPreferences.getString("pin","");

        monthofyear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month = (String) parent.getItemAtPosition(position);
                monthint = Monthsclass.getMonthInYear(month);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        yearpicked.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yeartxt = (String) parent.getItemAtPosition(position);
                year = getItem(yeartxt);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMonthlyReports(pin,monthint,year);
                Log.d("TAG","mONTH IS"+month+year+pin);
                date.setText(month+" "+year);
                Log.d("TAG","year is"+year);
                Log.d("TAG","monthint is "+monthint);
            }
        });
        return root;
    }
    private void showMonthlyReports(String pin, int month, int year){
        salesoperations.open();
        stockoperations.open();
        expenseoperations.open();
        salestxt = salesoperations.getMonthlySales(pin,month,year);
        expensetxt = expenseoperations.getMonthlyExpenses(pin,month,year);
        stocktxt = stockoperations.getMonthlyStock(pin, month, year);

        sales = getItem(salestxt);
        expense = getItem(expensetxt);
        stock = getItem(stocktxt);
        Log.d("TAG","Sales txt is"+salestxt);
        Log.d("TAG","Sales txt is"+expensetxt);
        Log.d("TAG","Sales txt is"+stocktxt);
        profit = sales-expense;
        profittxt = String.valueOf(profit);

        //dailysales,totalstock,totalexpenses,salesanalysis,outcome;
        if(expense>sales){
            advice = "Loss";
        }
        else {
            advice = "Profit";
        }
        monthlysales.setText(salestxt);
        monthlystock.setText(stocktxt);
        monthlyexpenses.setText(expensetxt);
        salesanalysis.setText(advice);
        outcome.setText(profittxt);
    }

    private int getItem(String txt){
        if(txt != null){
            float txtf = Float.valueOf(txt);
            int value = (int) txtf;
            return value;
        }
        else {
            return 0;
        }
    }


}
