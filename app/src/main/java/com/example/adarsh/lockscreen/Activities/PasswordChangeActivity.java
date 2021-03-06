package com.example.adarsh.lockscreen.Activities;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adarsh.lockscreen.Utilities.MyAdmin;
import com.example.adarsh.lockscreen.R;

public class PasswordChangeActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    TextView masterpass, devicepass;
    DevicePolicyManager deviceManger;
    ActivityManager activityManager;
    ComponentName compName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        deviceManger = (DevicePolicyManager)getSystemService(
                Context.DEVICE_POLICY_SERVICE);
        activityManager = (ActivityManager)getSystemService(
                Context.ACTIVITY_SERVICE);
        compName = new ComponentName(this, MyAdmin.class);

        sharedPreferences = getSharedPreferences("MASTERDATA", Context.MODE_PRIVATE);
        masterpass = (TextView) findViewById(R.id.massterpass);
        devicepass = (TextView) findViewById(R.id.devicepass);
        masterpass.setText(sharedPreferences.getString("masterpass", "0000"));
        devicepass.setText(sharedPreferences.getString("generatedpass", "-"));
        findViewById(R.id.changedevice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupRequestDevice();
            }
        });
        findViewById(R.id.changemaster).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupRequestMaster();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private void popupRequestDevice() {
        LayoutInflater layoutInflater = LayoutInflater.from(PasswordChangeActivity.this);

        View promptView = layoutInflater.inflate(R.layout.popup_layout_device, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PasswordChangeActivity.this);

        // set prompts.xml to be the layout file of the alertdialog builder
        alertDialogBuilder.setView(promptView);

        final EditText input = (EditText) promptView.findViewById(R.id.userInput);

        // setup a dialog window
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(input.getText().toString().equals("") || input.getText().toString().isEmpty() || input.getText().toString() == null) {
                            Toast.makeText(getApplicationContext(), "Password cant be empty", Toast.LENGTH_LONG).show();
                        }

                        else {
                            SharedPreferences.Editor editor = getSharedPreferences("PASS", 0).edit();
                            editor.putString("password", input.getText().toString());
                            editor.putBoolean("Changed", true);
                            editor.commit();

                            editor = sharedPreferences.edit();
                            editor.putString("generatedpass", input.getText().toString());
                            devicepass.setText(input.getText().toString());
                            editor.commit();

                            deviceManger.setPasswordQuality(
                                    compName, DevicePolicyManager.PASSWORD_QUALITY_UNSPECIFIED);
                            deviceManger.setPasswordMinimumLength(compName, 4);
                            deviceManger.resetPassword(input.getText().toString(),
                                    DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);
                        }
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alertD = alertDialogBuilder.create();

        alertD.show();
    }

    private void popupRequestMaster() {
        LayoutInflater layoutInflater = LayoutInflater.from(PasswordChangeActivity.this);

        View promptView = layoutInflater.inflate(R.layout.popup_layout, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PasswordChangeActivity.this);

        // set prompts.xml to be the layout file of the alertdialog builder
        alertDialogBuilder.setView(promptView);

        final EditText input = (EditText) promptView.findViewById(R.id.userInput);

        // setup a dialog window
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(input.getText().toString().equals("") || input.getText().toString().isEmpty() || input.getText().toString() == null) {
                            Toast.makeText(getApplicationContext(), "Password cant be empty", Toast.LENGTH_LONG).show();
                        }
                        else {
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("masterpass", input.getText().toString());
                            masterpass.setText(input.getText().toString());
                            editor.commit();
                        }
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alertD = alertDialogBuilder.create();

        alertD.show();
    }

}
