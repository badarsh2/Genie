package com.example.adarsh.lockscreen;


import android.app.NotificationManager;
import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

public class MyAdmin extends DeviceAdminReceiver {


    static SharedPreferences getSamplePreferences(Context context) {
        return context.getSharedPreferences(
                DeviceAdminReceiver.class.getName(), 0);
    }

    static String PREF_PASSWORD_QUALITY = "password_quality";
    static String PREF_PASSWORD_LENGTH = "password_length";
    static String PREF_MAX_FAILED_PW = "max_failed_pw";

    void showToast(Context context, CharSequence msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
    }

    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        return "This is an optional message to warn the user about disabling.";
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
    }

    @Override
    public void onPasswordChanged(Context context, Intent intent) {
        SharedPreferences prefs = context.getSharedPreferences("PASS", 0);
        boolean changed = prefs.getBoolean("Changed", true);
        if(!changed) {
            Intent intent_change = new Intent(context, PasswordChange.class);
            intent_change.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent_change.putExtra("passchange", 1);
            context.startActivity(intent_change);
        }
        SharedPreferences.Editor editor = context.getSharedPreferences("PASS", 0).edit();
        editor.putBoolean("Changed", false);
        editor.commit();
    }

    @Override
    public void onPasswordSucceeded(Context context, Intent intent) {
        SharedPreferences prefs = context.getSharedPreferences("PASS", 0);
        boolean locked = prefs.getBoolean("Locked", false);
        if(locked) {
            showToast(context, "Password succeeded");
            Intent intent_change = new Intent(context, LockScreenActivity.class);
            intent_change.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent_change);
            NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.cancelAll();
        }
        SharedPreferences.Editor editor = context.getSharedPreferences("PASS", 0).edit();
        editor.putBoolean("Locked", false);
        editor.commit();
    }

}