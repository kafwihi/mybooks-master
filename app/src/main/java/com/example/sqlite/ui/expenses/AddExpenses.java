package com.example.sqlite.ui.expenses;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sqlite.R;
import com.example.sqlite.database.Expensestable;
import com.example.sqlite.helpers.Alerts;
import com.example.sqlite.helpers.Patterns;
import com.example.sqlite.helpers.SessionManager;
import com.example.sqlite.operations.Expenseoperations;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class AddExpenses extends Fragment {

    Patterns patterns = new Patterns();
    private EditText expensecost,expensedescription;
    private Spinner expensetype;
    private Button addexpense;
    private ArrayAdapter<CharSequence> adapter;
    SessionManager sessionManager;
    SharedPreferences sharedPreferences;
    Alerts alerts;
    String pin;
    String typeofexpense;
    String cost;
    String description;
    int dayofmonth;
    int month;
    int year;
    Expenseoperations expenseoperations;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_addexpenses, container, false);

        expensecost = root.findViewById(R.id.cost);
        expensedescription = root.findViewById(R.id.description);
        expensetype = root.findViewById(R.id.expensetype);
        addexpense = root.findViewById(R.id.addexpense);
        expenseoperations = new Expenseoperations(getActivity());

        adapter = ArrayAdapter.createFromResource(getActivity(),R.array.expensetypes,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        expensetype.setAdapter(adapter);

        alerts = new Alerts(getActivity());

        expensetype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeofexpense = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sessionManager = new SessionManager(getActivity());
        sharedPreferences = this.getActivity().getSharedPreferences("LoginPref",Context.MODE_PRIVATE);
        pin = sharedPreferences.getString("pin","");

        addexpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cost = expensecost.getText().toString();
                description = expensedescription.getText().toString();
                Calendar calendar = Calendar.getInstance();
                dayofmonth = calendar.get(Calendar.DATE);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);
                String date = dayofmonth+"/"+month+"/"+year;


                if(cost.isEmpty()||description.isEmpty()){
                    String message = "Add all fields";
//                    Toast t = Toast.makeText(getActivity(),message,Toast.LENGTH_LONG);
//                    t.show();
                    alerts.showDialog(message);
                }
                else if(typeofexpense.isEmpty()){
                    String message = "Add all fields";
//                    Toast t = Toast.makeText(getActivity(),message,Toast.LENGTH_LONG);
//                    t.show();
                    alerts.showDialog(message);
                }
                else {
                    expenseoperations.open();
                    expenseoperations.addexpense(pin,typeofexpense,description,cost,date,month,year);
                    String message = "Expense added successfully";
                    expensecost.setText("");
                    expensedescription.setText("");
//                    Toast t = Toast.makeText(getActivity(),message,Toast.LENGTH_LONG);
//                    t.show();
                    alerts.showDialog(message);
                }
            }
        });

        return root;
    }
}