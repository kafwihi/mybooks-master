package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sqlite.helpers.Alerts;
import com.example.sqlite.helpers.Patterns;
import com.example.sqlite.operations.Useroperations;

public class Register extends AppCompatActivity {
    Button registerbtn;
    EditText firstname,lastname,pin;
    String firstnametxt,lastnametxt,pintxt;
    Alerts alerts = new Alerts(this);
    Patterns patterns = new Patterns();
    Useroperations useroperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        pin = (EditText) findViewById(R.id.pinno);
        registerbtn = (Button) findViewById(R.id.register);

        useroperations = new Useroperations(this);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               firstnametxt = firstname.getText().toString();
               lastnametxt = lastname.getText().toString();
               pintxt = pin.getText().toString();

               if(firstnametxt.isEmpty()||lastnametxt.isEmpty()||pintxt.isEmpty()){
                   String message = "Enter all the details";
                   alerts.showDialog(message);
               }
               else if(!firstnametxt.matches(patterns.namepattern)){
                   String message = "Enter a valid name";
                   alerts.showDialog(message);
               }
               else if(!lastnametxt.matches(patterns.namepattern)){
                   String message = "Enter a valid name";
                   alerts.showDialog(message);
               }
               else if((pintxt.length()>4)||(pintxt.length()<4)){
                   String message = "Pin length should be exactly four characters";
                   alerts.showDialog(message);
               }
               else{
                  //add user
                   useroperations.open();
                   if(useroperations.addusers(firstnametxt,lastnametxt,pintxt)){
                       String message = "User Added Successfully";
                       alerts.showDialog(message);
                       pin.setText("");
                       firstname.setText("");
                       lastname.setText("");
                   }
                   else{
                       String message = "A user with that PIN already exists";
                       alerts.showDialog(message);
                   }

               }
            }
        });

    }
}
