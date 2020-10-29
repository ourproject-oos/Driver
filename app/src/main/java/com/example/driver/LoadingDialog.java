package com.example.driver;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class LoadingDialog {

    private Activity activity;
    private AlertDialog dialog;

    public LoadingDialog(Activity myActivity)
    {
        activity = myActivity;
    }

    public void startLoadingDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loadingdialog,null));
        builder.setCancelable(true);

        dialog = builder.create();
        dialog.show();

    }


    public void dismissDialog()
    {
        dialog.dismiss();
    }
}
