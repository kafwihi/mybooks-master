package com.example.sqlite.ui.home;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sqlite.Adapters.BusinessAdapter;
import com.example.sqlite.MainActivity;
import com.example.sqlite.ManageBusiness;
import com.example.sqlite.R;
import com.example.sqlite.database.BooksDB;
import com.example.sqlite.helpers.Patterns;
import com.example.sqlite.helpers.SessionManager;
import com.example.sqlite.objects.Businesses;
import com.example.sqlite.objects.Users;
import com.example.sqlite.operations.Businessoperations;
import com.example.sqlite.operations.Useroperations;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    SharedPreferences preferences;
    SessionManager sessionManager;
    Useroperations useroperations;
    Businesses businesses;
    Businessoperations businessoperations;
    private List businesslist = new ArrayList();
    private ArrayList<String> id = new ArrayList<String>();
    private ArrayList<String> busname = new ArrayList<String>();
    private ArrayList<String> busdesc = new ArrayList<String>();
    BooksDB booksDB;
    SQLiteDatabase database;
    ListView listView;

    String fullname;
    Users users;
    String bizname,bizdesc;
    String pin;
    final Patterns patterns = new Patterns();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.welcometxt);
        final Button createbusiness = root.findViewById(R.id.createbusiness);
        listView = root.findViewById(R.id.businesses);
        sessionManager = new SessionManager(getActivity());
        useroperations = new Useroperations(getActivity());
        businessoperations = new Businessoperations(getActivity());
        preferences = this.getActivity().getSharedPreferences("LoginPref", Context.MODE_PRIVATE);
        pin = preferences.getString("pin","");
        if(pin.isEmpty()){
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }


        useroperations.open();
        fullname = useroperations.getDetails(pin);
        textView.setText("Welcome "+fullname);
        showBusinesses(pin);
        createbusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View create = getActivity().getLayoutInflater().inflate(R.layout.create_business,null);
                final Dialog dialog = new Dialog(getActivity());
                dialog.setCancelable(true);
                dialog.setContentView(create);
                dialog.show();
                final EditText businessname = (EditText) create.findViewById(R.id.bizname);
                final EditText businessdescription = (EditText) create.findViewById(R.id.bizdesc);

                final Button addbiz = (Button) create.findViewById(R.id.createbiz);
                addbiz.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bizname = businessname.getText().toString();
                        bizdesc = businessdescription.getText().toString();

                        if(bizname.isEmpty()||bizdesc.isEmpty()){
                            Toast t = Toast.makeText(getActivity(),"Enter all details please",Toast.LENGTH_LONG);
                            t.show();
                        }
                        else {
                           businessoperations.open();
                           businessoperations.addBiz(pin,bizname,bizdesc);
                           businessoperations.close();
                           Toast t = Toast.makeText(getActivity(),"Business Added Successfully",Toast.LENGTH_LONG);
                           t.show();
                           dialog.dismiss();
                        }

                    }
                });

            }
        });
        return root;
    }
    private void showBusinesses(String pin){
        booksDB = new BooksDB(getActivity());
        database = booksDB.getReadableDatabase();
        final Cursor res = database.rawQuery("Select id,business_name,business_description " +
                "from businesses where pin="+pin,null);
        if(res.moveToFirst()){
            do {
                busname.add(res.getString(res.getColumnIndex("business_name")));
                busdesc.add(res.getString(res.getColumnIndex("business_description")));
                //id.add(res.getString(res.getColumnIndex("id")));
            }
            while (res.moveToNext());
        }
        BusinessAdapter businessAdapter = new BusinessAdapter(getActivity(),busname,busdesc);
        listView.setAdapter(businessAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ManageBusiness.class);
                if(res.moveToFirst()){
                    res.moveToPosition(position);
                    intent.putExtra("KEY",res.getString(0));
                    startActivity(intent);
                }
            }
        });
    }

}