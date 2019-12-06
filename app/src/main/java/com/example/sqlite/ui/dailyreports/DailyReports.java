package com.example.sqlite.ui.dailyreports;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sqlite.R;
import com.example.sqlite.helpers.Monthsclass;
import com.example.sqlite.helpers.SessionManager;
import com.example.sqlite.operations.Expenseoperations;
import com.example.sqlite.operations.Salesoperations;
import com.example.sqlite.operations.Stockoperations;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.Calendar;

public class DailyReports extends Fragment {
    TextView datetoday,dailysales,totalstock,totalexpenses,salesanalysis,outcome;
    Expenseoperations expenseoperations;
    Salesoperations salesoperations;
    Stockoperations stockoperations;
    SessionManager sessionManager;
    SharedPreferences sharedPreferences;
    String salestxt,stocktxt,expensetxt,profittxt,advice;
    int sales,stock,expense,profit;
    String pin;
    int year,dayofmonth,month;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.daily_reports_fragment, container, false);
        datetoday = root.findViewById(R.id.date);
        dailysales = root.findViewById(R.id.dailysales);
        totalstock = root.findViewById(R.id.totalstock);
        totalexpenses = root.findViewById(R.id.totalexpenses);
        salesanalysis = root.findViewById(R.id.salesanalysis);
        outcome = root.findViewById(R.id.outcome);

        salesoperations = new Salesoperations(getActivity());
        stockoperations = new Stockoperations(getActivity());
        expenseoperations = new Expenseoperations(getActivity());

        sessionManager = new SessionManager(getActivity());
        sharedPreferences = this.getActivity().getSharedPreferences("LoginPref", Context.MODE_PRIVATE);
        pin = sharedPreferences.getString("pin","");

        Calendar calendar = Calendar.getInstance();
        dayofmonth = calendar.get(Calendar.DATE);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        String monthstring = Monthsclass.getMonthFromId(month);
        String datetxt = monthstring+", "+dayofmonth+", "+year;
        String date = dayofmonth+"/"+month+"/"+year;
        showReport(pin,date);
        datetoday.setText(datetxt);

        return root;
    }
    private void showReport(String pin,String date){
        salestxt = salesoperations.getDailySales(pin, date);
        expensetxt = expenseoperations.getDailyExpenses(pin, date);
        stocktxt = stockoperations.getDailyStock(pin, date);

        sales = getItem(salestxt);
        expense = getItem(expensetxt);
        stock = getItem(stocktxt);

        profit = sales-expense;
        profittxt = String.valueOf(profit);

        //dailysales,totalstock,totalexpenses,salesanalysis,outcome;
        if(expense>sales){
            advice = "Loss";
        }
        else {
            advice = "Profit";
        }
        dailysales.setText(salestxt);
        totalstock.setText(stocktxt);
        totalexpenses.setText(expensetxt);
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
