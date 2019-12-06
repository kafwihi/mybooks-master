package com.example.sqlite;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.sqlite.helpers.Alerts;
import com.example.sqlite.helpers.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Environment;
import android.view.MenuItem;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Dashboard extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    SessionManager sessionManager;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String pin;
    Alerts alerts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sessionManager = new SessionManager(getApplicationContext());
        preferences = getSharedPreferences("LoginPref", Context.MODE_PRIVATE);
        alerts = new Alerts(this);
        pin = preferences.getString("pin","");
        if(pin==""){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_stock, R.id.nav_expenses,
                R.id.nav_addsales, R.id.todaysexpenses, R.id.allstock,R.id.allexpenses,R.id.todayssales,
                R.id.dailystock,R.id.allsales,R.id.nav_dailyreport,R.id.nav_monthlyreport,R.id.searchreports)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            logout();
        }
//        else if(id == R.id.backup){
//            try {
//                backup();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
//    private void backup() throws IOException {
//        String file = "dbcopy";
//        final String inFileName = "/data/data/com.example.sqlite/databases/books.db";
//        FileOutputStream fout = openFileOutput(inFileName,MODE_PRIVATE);
//        fout.write(file.getBytes());
//        fout.close();
//        String message = "Data backed up successfully";
//        alerts.showDialog(message);
//    }
    private void logout(){
        editor = preferences.edit();
        editor.clear();
        editor.apply();

        //start a new activity
        sessionManager.setLogin(false);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }


}
