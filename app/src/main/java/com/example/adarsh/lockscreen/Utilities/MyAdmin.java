package com.example.adarsh.lockscreen.Utilities;


import android.app.NotificationManager;
import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.adarsh.lockscreen.Activities.LockScreenActivity;
import com.example.adarsh.lockscreen.Activities.PasswordChangeActivity;

public class MyAdmin extends DeviceAdminReceiver {

    @Override
    public void onPasswordChanged(Context context, Intent intent) {
        SharedPreferences prefs = context.getSharedPreferences("PASS", 0);
        boolean changed = prefs.getBoolean("Changed", true);
        if(!changed) {
            Intent intent_change = new Intent(context, LockScreenActivity.class);
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
            Intent intent_change = new Intent(context, PasswordChangeActivity.class);
            intent_change.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent_change);
            NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.cancel(1);
        }
        SharedPreferences.Editor editor = context.getSharedPreferences("PASS", 0).edit();
        editor.putBoolean("Locked", false);
        editor.commit();
    }

    @Override
    public void onPasswordFailed(Context context, Intent intent) {
        NotificationGenerator notification_button = new NotificationGenerator(context);
        notification_button.addNotification();
    }

}