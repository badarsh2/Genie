package com.example.adarsh.lockscreen.Activities;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adarsh.lockscreen.Utilities.MyAdmin;
import com.example.adarsh.lockscreen.R;


public class LockScreenActivity extends ActionBarActivity {


    DevicePolicyManager deviceManger;
    ActivityManager activityManager;
    ComponentName compName;
    TextView masterpass;
    Toolbar toolbar;
    ImageView credits;
    SharedPreferences sharedPreferences;
    private int MY_PERMISSIONS_REQUEST_RECEIVE_SMS = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        //startService(new Intent(this,ServerLockService.class));
        deviceManger = (DevicePolicyManager)getSystemService(
                Context.DEVICE_POLICY_SERVICE);
        activityManager = (ActivityManager)getSystemService(
                Context.ACTIVITY_SERVICE);
        compName = new ComponentName(this, MyAdmin.class);
        Intent intent = new Intent(DevicePolicyManager
                .ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                compName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "Additional text explaining why this needs to be added.");
        startActivityForResult(intent, 1);
        // setContentView(R.layout.activity_lock_screen);
        // notif_display();

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(LockScreenActivity.this, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(LockScreenActivity.this,
                    Manifest.permission.RECEIVE_SMS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(LockScreenActivity.this,
                        new String[]{Manifest.permission.RECEIVE_SMS},
                        MY_PERMISSIONS_REQUEST_RECEIVE_SMS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        sharedPreferences = getSharedPreferences("MASTERDATA", Context.MODE_PRIVATE);
        masterpass = (TextView) findViewById(R.id.massterpass);
        findViewById(R.id.enterpwdpage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(masterpass.getText().toString().equals((sharedPreferences.getString("masterpass", "0000")))) {
                    startActivity(new Intent(LockScreenActivity.this, PasswordChangeActivity.class));
                }
                else {
                    Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_LONG).show();
                }
            }
        });
        Intent i = getIntent();
        if(i.getIntExtra("passchange", 0) == 1) {
            new AlertDialog.Builder(LockScreenActivity.this)
                    .setTitle("Password changed")
                    .setMessage("A password change has been detected. Please update the password in Genie also.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .show();
        }

        credits = (ImageView) findViewById(R.id.credits);
        credits.setColorFilter(Color.parseColor("#EEEEEE"));
        credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://play.google.com/store/apps/developer?id=Flat+Earth+Studios&hl=en"));
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lock_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

}


