package com.example.adarsh.lockscreen.Utilities;

import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class ReceiveSMS extends BroadcastReceiver
{
    DevicePolicyManager deviceManger;
    ActivityManager activityManager;
    ComponentName compName;
    static SharedPreferences sharedPreferences;

    public static Integer tryParse(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public void savePassword(Context context, String password) {
        Log.d("hhh", password);
        SharedPreferences.Editor editor = context.getSharedPreferences("PASS", 0).edit();
        editor.putString("password", password);
        editor.commit();
        deviceManger.setPasswordQuality(
                compName, DevicePolicyManager.PASSWORD_QUALITY_UNSPECIFIED);
        deviceManger.setPasswordMinimumLength(compName, 4);
        deviceManger.resetPassword(password,
                DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);
    }

    public void lockDevice() {
        boolean active = deviceManger.isAdminActive(compName);
        if (active) {
            deviceManger.lockNow();
        }
    }

    private int generateRandomInt(int min, int max) {
        return min + (int)(Math.random() * (max - min));
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        deviceManger = (DevicePolicyManager)context.getSystemService(
                Context.DEVICE_POLICY_SERVICE);
        activityManager = (ActivityManager)context.getSystemService(
                Context.ACTIVITY_SERVICE);
        compName = new ComponentName(context, MyAdmin.class);
        sharedPreferences = context.getSharedPreferences("MASTERDATA", Context.MODE_PRIVATE);

        Bundle bundle = intent.getExtras();
        SmsMessage[] recievedMsgs = null;
        String incoming = "";
        if (bundle != null)
        {

            Object[] pdus = (Object[]) bundle.get("pdus");
            recievedMsgs = new SmsMessage[pdus.length];
            for (int i=0; i<pdus.length;i++){
                recievedMsgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                incoming = recievedMsgs[i].getMessageBody();
                Log.d("hhhh", incoming);
            }

            if(incoming.length() != 0) {
                SharedPreferences prefs = context.getSharedPreferences("PASS", 0);
                String prev = prefs.getString("password", "0000");
                Log.d("hhh", prev);
                if(incoming.equals(prev)){
                    if(tryParse(incoming) != null){
                        int val = tryParse(incoming), max = (int)Math.pow(10, incoming.length()), min = (int)Math.pow(10, incoming.length() - 1);
                        int random_num = generateRandomInt(min, max);
                        if(random_num >= val){
                            random_num += 1;
                        }

                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("generatedpass", random_num + "");
                        editor.commit();
                        editor = context.getSharedPreferences("PASS", 0).edit();
                        editor.putBoolean("Locked", true);
                        editor.putBoolean("Changed", true);
                        editor.commit();
                        savePassword(context, random_num + "");
                        notifDisplay(context);

                        lockDevice();
                    }
                    else {
                        char[] message = incoming.toCharArray();
                        for(int i = 0; i < incoming.length(); i++) {
                            if(message[i]<='Z' && message[i]>='A') {
                                message[i] = (char) ('A' + (message[i] - 'A' + generateRandomInt(1, 26)) % 26);
                            }
                            else if(message[i]<='z' && message[i]>='a') {
                                message[i] = (char) ('a' + (message[i] - 'a' + generateRandomInt(1, 26)) % 26);
                            }
                        }
                        Log.d("hhhh", new String(message));
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("generatedpass", new String(message));
                        editor.commit();
                        editor = context.getSharedPreferences("PASS", 0).edit();
                        editor.putBoolean("Locked", true);
                        editor.putBoolean("Changed", true);
                        editor.commit();

                        savePassword(context, new String(message));

                        notifDisplay(context);
                        lockDevice();
                    }
                }
            }
        }
    }

    private void notifDisplay(Context context) {
        NotificationGenerator notification_button = new NotificationGenerator(context);
        notification_button.addNotification();
    }
}