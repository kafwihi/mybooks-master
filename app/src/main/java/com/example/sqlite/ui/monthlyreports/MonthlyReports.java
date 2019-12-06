package com.example.sqlite.ui.monthlyreports;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.CollapsibleActionView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sqlite.R;
import com.example.sqlite.helpers.Monthsclass;
import com.example.sqlite.helpers.SessionManager;
import com.example.sqlite.operations.Expenseoperations;
import com.example.sqlite.operations.Salesoperations;
import com.example.sqlite.operations.Stockoperations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;

public class MonthlyReports extends Fragment {
    TextView date,monthlysales,monthlyexpenses,monthlystock,salesanalysis,outcome;
    Button download;
   // Button generatepdf;
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
        View root = inflater.inflate(R.layout.monthly_reports_fragment, container, false);
        date = root.findViewById(R.id.date);
        download = root.findViewById(R.id.download);
        monthlysales = root.findViewById(R.id.monthlysales);
        monthlyexpenses = root.findViewById(R.id.monthlyexpenses);
        monthlystock = root.findViewById(R.id.monthlystock);
        salesanalysis = root.findViewById(R.id.salesanalysis);
        outcome = root.findViewById(R.id.outcome);
        //generatepdf = root.findViewById(R.id.generatereports);

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

        date.setText(monthstring+" "+year);
        showMonthlyReports(pin,month,year);

//        generatepdf.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return root;
    }
    private void showMonthlyReports(String pin, int month, int year){
        salestxt = salesoperations.getMonthlySales(pin,month,year);
        expensetxt = expenseoperations.getMonthlyExpenses(pin,month,year);
        stocktxt = stockoperations.getMonthlyStock(pin, month, year);

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
        monthlysales.setText(salestxt);
        monthlystock.setText(stocktxt);
        monthlyexpenses.setText(expensetxt);
        salesanalysis.setText(advice);
        outcome.setText(profittxt);
    }
//    private void generateDoc(String pin, int month, int year){
//        salestxt = salesoperations.getMonthlySales(pin,month,year);
//        expensetxt = expenseoperations.getMonthlyExpenses(pin,month,year);
//        stocktxt = stockoperations.getMonthlyStock(pin, month, year);
//
//        sales = getItem(salestxt);
//        expense = getItem(expensetxt);
//        stock = getItem(stocktxt);
//
//        profit = sales-expense;
//        profittxt = String.valueOf(profit);
//
//        //dailysales,totalstock,totalexpenses,salesanalysis,outcome;
//        if(expense>sales){
//            advice = "Loss";
//        }
//        else {
//            advice = "Profit";
//        }
//        File file;
//        FileOutputStream fileOutputStream;
//        file = getFilesDir();
//        fileOutputStream = openFileOutput("Code.txt",Context.MODE_PRIVATE);
//    }
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
