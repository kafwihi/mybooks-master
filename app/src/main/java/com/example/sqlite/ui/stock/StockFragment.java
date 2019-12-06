package com.example.sqlite.ui.stock;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sqlite.R;
import com.example.sqlite.helpers.Alerts;
import com.example.sqlite.helpers.Patterns;
import com.example.sqlite.helpers.SessionManager;
import com.example.sqlite.operations.Salesoperations;
import com.example.sqlite.operations.Stockoperations;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;

public class StockFragment extends Fragment {
    Patterns patterns = new Patterns();
    private EditText item,itemdesc,cost;
    private Button addstock;
    private String itemname,description,itemcost,pin;
    int year,dayofmonth,month;
    Stockoperations stockoperations;
    SessionManager sessionManager;
    SharedPreferences sharedPreferences;
    Alerts alerts;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_stock, container, false);
        item = root.findViewById(R.id.itemname);
        itemdesc = root.findViewById(R.id.itemdesc);
        cost = root.findViewById(R.id.cost);
        addstock = root.findViewById(R.id.addstock);

        alerts = new Alerts(getActivity());
        stockoperations = new Stockoperations(getActivity());

        sessionManager = new SessionManager(getActivity());
        sharedPreferences = this.getActivity().getSharedPreferences("LoginPref", Context.MODE_PRIVATE);
        pin = sharedPreferences.getString("pin","");


        addstock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemname = item.getText().toString();
                description = itemdesc.getText().toString();
                itemcost = cost.getText().toString();

                Calendar calendar = Calendar.getInstance();
                dayofmonth = calendar.get(Calendar.DATE);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);
                String date = dayofmonth+"/"+month+"/"+year;


                if(itemname.isEmpty()||description.isEmpty()||itemcost.isEmpty()){
                    String message = "Please enter all fields";
                    alerts.showDialog(message);
                }
                else{
                   stockoperations.open();
                   stockoperations.addStock(pin,itemname,description,itemcost,date,year,month);
                   stockoperations.close();
                    String message = "Stock added successfully";
                    alerts.showDialog(message);
                    item.setText("");
                    itemdesc.setText("");
                    cost.setText("");
                }
            }
        });

        return root;
    }
}