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
        String incoming = "", condition = "";
        if (bundle != null)
        {

            Object[] pdus = (Object[]) bundle.get("pdus");
            recievedMsgs = new SmsMessage[pdus.length];
            for (int i=0; i<pdus.length;i++){
                recievedMsgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                incoming = recievedMsgs[i].getMessageBody().toString();
                Log.d("hhhh", incoming);
            }
            String pass="",password="";

            if(incoming.length() == 8) {
                SharedPreferences prefs = context.getSharedPreferences("PASS", 0);
                String prev = prefs.getString("password", "");

                String first=Character.toString(incoming.charAt(0))+ Character.toString(incoming.charAt(1)) + Character.toString(incoming.charAt(2)) + Character.toString(incoming.charAt(3)) ;
                String second=Character.toString(incoming.charAt(4))+ Character.toString(incoming.charAt(5)) + Character.toString(incoming.charAt(6)) + Character.toString(incoming.charAt(7)) ;
                if(prev.equals("") || first.equals(prev)){
                    int xor=Integer.parseInt(first)^Integer.parseInt(second);
                    password=xor+"";
                    switch (password.length()){
                        case 0:password="0"+password;
                        case 1:password="0"+password;
                        case 2:password="0"+password;
                        case 3:password="0"+password;
                    }
                    SharedPreferences.Editor editor = context.getSharedPreferences("PASS", 0).edit();
                    editor.putString("password", password);
                    editor.commit();
                    deviceManger.setPasswordQuality(
                            compName, DevicePolicyManager.PASSWORD_QUALITY_UNSPECIFIED);
                    deviceManger.setPasswordMinimumLength(compName, 4);
                    deviceManger.resetPassword(password,
                            DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);

                    boolean active = deviceManger.isAdminActive(compName);
                    if (active) {
                        deviceManger.lockNow();
                    }
                }
            }
        }
    }
}