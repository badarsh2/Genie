package com.example.adarsh.lockscreen;

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
import android.widget.Toast;

public class ReceiveSMS extends BroadcastReceiver
{
    DevicePolicyManager deviceManger;
    ActivityManager activityManager;
    ComponentName compName;

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
                if(incoming.equals(prev)){
                    if(tryParse(incoming) != null){
                        int val = tryParse(incoming), max = (int)Math.pow(10, incoming.length()), min = (int)Math.pow(10, incoming.length() - 1);
                        int random_num = generateRandomInt(min, max);
                        if(random_num >= val){
                            random_num += 1;
                        }
                        savePassword(context, random_num + "");
                        lockDevice();
                    }
                    else {
                        char[] message = incoming.toCharArray();
                        for(int i = 0; i < incoming.length(); i++) {
                            message[i] = (char)((message[i]+generateRandomInt(0, 27))%128);
                        }
                        savePassword(context, new String(message));
                        lockDevice();
                    }
                }
            }
        }
    }
}