package com.example.sqlite.ui.addsales;

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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;

public class AddSales extends Fragment {
    Alerts alerts;
    Patterns patterns = new Patterns();
    private EditText itemname;
    private EditText itemdesc;
    private EditText sellingprice;
    private Button addbtn;
    private String item,description,price;
    private String pin;
    Salesoperations salesoperations;
    SessionManager sessionManager;
    SharedPreferences sharedPreferences;
    int dayofmonth,year,month;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_addsales, container, false);
        itemname = root.findViewById(R.id.itemname);
        itemdesc = root.findViewById(R.id.itemdesc);
        sellingprice = root.findViewById(R.id.sellingprice);
        salesoperations = new Salesoperations(getActivity());
        alerts = new Alerts(getActivity());

        sessionManager = new SessionManager(getActivity());
        sharedPreferences = this.getActivity().getSharedPreferences("LoginPref", Context.MODE_PRIVATE);
        pin = sharedPreferences.getString("pin","");

        addbtn = root.findViewById(R.id.addsales);

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = itemname.getText().toString();
                description = itemdesc.getText().toString();
                price = sellingprice.getText().toString();


                Calendar calendar = Calendar.getInstance();
                dayofmonth = calendar.get(Calendar.DATE);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);
                String date = dayofmonth+"/"+month+"/"+year;


                if(item.isEmpty()||description.isEmpty()||price.isEmpty()){
                    String message = "Please enter all the fields";
                    alerts.showDialog(message);
                }
                else {
                    salesoperations.open();
                    salesoperations.addSales(pin,item,description,price,date,year,month);
                    salesoperations.close();
                    String message = "Sale added successfully";
                    alerts.showDialog(message);
                    itemname.setText("");
                    itemdesc.setText("");
                    sellingprice.setText("");
                }

            }
        });
        return root;
    }
}