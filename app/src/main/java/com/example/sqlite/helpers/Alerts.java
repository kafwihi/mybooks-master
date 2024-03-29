package com.example.sqlite.helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class Alerts {
    Context ctxt;
    public Alerts(Context ctxt){
        this.ctxt = ctxt;
    }
    public void showDialog(String message){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctxt);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setNegativeButton("OKAY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialogBuilder.show();
    }
}
