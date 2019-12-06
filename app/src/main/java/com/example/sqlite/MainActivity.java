package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sqlite.helpers.Alerts;
import com.example.sqlite.helpers.SessionManager;
import com.example.sqlite.operations.Useroperations;

public class MainActivity extends AppCompatActivity {
    private EditText pin;
    private Button login,register;
    Alerts alerts = new Alerts(this);
    Useroperations useroperations;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pin = (EditText) findViewById(R.id.pinno);
        login = (Button) findViewById(R.id.loginbutton);
        register= (Button) findViewById(R.id.registerbutton);

        useroperations = new Useroperations(this);
        sessionManager = new SessionManager(getApplicationContext());

        //LOGIN ACTION
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pintxt = pin.getText().toString();
                if(pintxt.isEmpty()){
                    String message = "Enter all details please";
                    alerts.showDialog(message);
                }
                else{
                    useroperations.userlogin(pintxt);
                    if(useroperations.userlogin(pintxt)){
                        //String pinres = res.getString(res.getColumnIndex("pin"));
                       // String message = "Login Successful ";
                       // alerts.showDialog(message);
                        SharedPreferences preferences = getSharedPreferences("LoginPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("pin",pintxt);
                        editor.apply();
                        sessionManager.setLogin(true);
                        Intent i = new Intent(getApplicationContext(),Dashboard.class);
                        startActivity(i);
                        pin.setText("");
                    }
                    else{
                        String message = "Invalid Pin. Try again.";
                        alerts.showDialog(message);
                    }
                }
            }
        });
        //register ACTION
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Register.class);
                startActivity(intent);
            }
        });

    }

}
