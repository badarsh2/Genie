package com.example.adarsh.lockscreen;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

public class ReceiveSMS extends BroadcastReceiver
{
    DevicePolicyManager deviceManger;
    ActivityManager activityManager;
    ComponentName compName;
    static String str;
    static RemoteViews contentView;
    static NotificationManager mNotificationManager;
    static TextView textpwd;
    static NotificationCompat.Builder notificationBuilder;
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
                if(incoming.equals(prev)){
                    if(tryParse(incoming) != null){
                        int val = tryParse(incoming), max = (int)Math.pow(10, incoming.length()), min = (int)Math.pow(10, incoming.length() - 1);
                        int random_num = generateRandomInt(min, max);
                        if(random_num >= val){
                            random_num += 1;
                        }
                        savePassword(context, random_num + "");

                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("generatedpass", random_num + "");
                        editor.commit();

                        notif_display(context);
                        lockDevice();
                    }
                    else {
                        char[] message = incoming.toCharArray();
                        for(int i = 0; i < incoming.length(); i++) {
                            message[i] = (char)((message[i]+generateRandomInt(0, 27))%128);
                        }
                        savePassword(context, new String(message));

                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("generatedpass", new String(message));
                        editor.commit();

                        notif_display(context);
                        lockDevice();
                    }
                }
            }
        }
    }

    private void notif_display(Context context) {
        int icon = R.mipmap.ic_launcher;
        long when = System.currentTimeMillis();
        str = "";
        mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        contentView = new RemoteViews(context.getPackageName(), R.layout.custom_notification);
        notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(icon)
                .setContent(contentView)
                .setContentTitle("Custom Notification")
                .setOngoing(true)
                .setWhen(when);
        notificationBuilder.setCustomBigContentView(contentView);

        Intent switchIntentzero = new Intent(context, switchButtonListenerzero.class);
        PendingIntent pendingSwitchIntentzero = PendingIntent.getBroadcast(context, 0,
                switchIntentzero, 0);
        contentView.setOnClickPendingIntent(R.id.btn0, pendingSwitchIntentzero);

        Intent switchIntentone = new Intent(context, switchButtonListenerone.class);
        PendingIntent pendingSwitchIntentone = PendingIntent.getBroadcast(context, 0,
                switchIntentone, 0);
        contentView.setOnClickPendingIntent(R.id.btn1, pendingSwitchIntentone);

        Intent switchIntenttwo = new Intent(context, switchButtonListenertwo.class);
        PendingIntent pendingSwitchIntenttwo = PendingIntent.getBroadcast(context, 0,
                switchIntenttwo, 0);
        contentView.setOnClickPendingIntent(R.id.btn2, pendingSwitchIntenttwo);

        Intent switchIntentthree = new Intent(context, switchButtonListenerthree.class);
        PendingIntent pendingSwitchIntentthree = PendingIntent.getBroadcast(context, 0,
                switchIntentthree, 0);
        contentView.setOnClickPendingIntent(R.id.btn3, pendingSwitchIntentthree);

        Intent switchIntentfour = new Intent(context, switchButtonListenerfour.class);
        PendingIntent pendingSwitchIntentfour = PendingIntent.getBroadcast(context, 0,
                switchIntentfour, 0);
        contentView.setOnClickPendingIntent(R.id.btn4, pendingSwitchIntentfour);

        Intent switchIntentfive = new Intent(context, switchButtonListenerfive.class);
        PendingIntent pendingSwitchIntentfive = PendingIntent.getBroadcast(context, 0,
                switchIntentfive, 0);
        contentView.setOnClickPendingIntent(R.id.btn5, pendingSwitchIntentfive);

        Intent switchIntentsix = new Intent(context, switchButtonListenersix.class);
        PendingIntent pendingSwitchIntentsix = PendingIntent.getBroadcast(context, 0,
                switchIntentsix, 0);
        contentView.setOnClickPendingIntent(R.id.btn6, pendingSwitchIntentsix);

        Intent switchIntentseven = new Intent(context, switchButtonListenerseven.class);
        PendingIntent pendingSwitchIntentseven = PendingIntent.getBroadcast(context, 0,
                switchIntentseven, 0);
        contentView.setOnClickPendingIntent(R.id.btn7, pendingSwitchIntentseven);

        Intent switchIntenteight = new Intent(context, switchButtonListenereight.class);
        PendingIntent pendingSwitchIntenteight = PendingIntent.getBroadcast(context, 0,
                switchIntenteight, 0);
        contentView.setOnClickPendingIntent(R.id.btn8, pendingSwitchIntenteight);

        Intent switchIntentnine = new Intent(context, switchButtonListenernine.class);
        PendingIntent pendingSwitchIntentnine = PendingIntent.getBroadcast(context, 0,
                switchIntentnine, 0);
        contentView.setOnClickPendingIntent(R.id.btn9, pendingSwitchIntentnine);

        Intent switchIntentbackspace = new Intent(context, switchButtonListenerbackspace.class);
        PendingIntent pendingSwitchIntentbackspace = PendingIntent.getBroadcast(context, 0,
                switchIntentbackspace, 0);
        contentView.setOnClickPendingIntent(R.id.backspace, pendingSwitchIntentbackspace);

        Intent switchIntentsubmit = new Intent(context, switchButtonListenersubmit.class);
        PendingIntent pendingSwitchIntentsubmit = PendingIntent.getBroadcast(context, 0,
                switchIntentsubmit, 0);
        contentView.setOnClickPendingIntent(R.id.submit, pendingSwitchIntentsubmit);

        contentView.setTextViewText(R.id.textpwd, "");
        mNotificationManager.notify(1, notificationBuilder.build());
    }

    public static class switchButtonListenerzero extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            str = str.concat("0");
            contentView.setTextViewText(R.id.textpwd, str);
            // notif_display();
            notificationBuilder.setCustomBigContentView(contentView);
            mNotificationManager.notify(1, notificationBuilder.build());
        }

    }

    public static class switchButtonListenerone extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            str = str.concat("1");
            contentView.setTextViewText(R.id.textpwd, str);
            // notif_display();
            notificationBuilder.setCustomBigContentView(contentView);
            mNotificationManager.notify(1, notificationBuilder.build());
        }

    }

    public static class switchButtonListenertwo extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            str = str.concat("2");
            contentView.setTextViewText(R.id.textpwd, str);
            // notif_display();
            notificationBuilder.setCustomBigContentView(contentView);
            mNotificationManager.notify(1, notificationBuilder.build());
        }

    }

    public static class switchButtonListenerthree extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            str = str.concat("3");
            contentView.setTextViewText(R.id.textpwd, str);
            // notif_display();
            notificationBuilder.setCustomBigContentView(contentView);
            mNotificationManager.notify(1, notificationBuilder.build());
        }

    }

    public static class switchButtonListenerfour extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            str = str.concat("4");
            contentView.setTextViewText(R.id.textpwd, str);
            // notif_display();
            notificationBuilder.setCustomBigContentView(contentView);
            mNotificationManager.notify(1, notificationBuilder.build());
        }

    }

    public static class switchButtonListenerfive extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            str = str.concat("5");
            contentView.setTextViewText(R.id.textpwd, str);
            // notif_display();
            notificationBuilder.setCustomBigContentView(contentView);
            mNotificationManager.notify(1, notificationBuilder.build());
        }

    }

    public static class switchButtonListenersix extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            str = str.concat("6");
            contentView.setTextViewText(R.id.textpwd, str);
            // notif_display();
            notificationBuilder.setCustomBigContentView(contentView);
            mNotificationManager.notify(1, notificationBuilder.build());
        }

    }

    public static class switchButtonListenerseven extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            str = str.concat("7");
            contentView.setTextViewText(R.id.textpwd, str);
            // notif_display();
            notificationBuilder.setCustomBigContentView(contentView);
            mNotificationManager.notify(1, notificationBuilder.build());
        }

    }

    public static class switchButtonListenereight extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            str = str.concat("8");
            contentView.setTextViewText(R.id.textpwd, str);
            // notif_display();
            notificationBuilder.setCustomBigContentView(contentView);
            mNotificationManager.notify(1, notificationBuilder.build());
        }

    }

    public static class switchButtonListenernine extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            str = str.concat("9");
            contentView.setTextViewText(R.id.textpwd, str);
            // notif_display();
            notificationBuilder.setCustomBigContentView(contentView);
            mNotificationManager.notify(1, notificationBuilder.build());
        }

    }

    public static class switchButtonListenerbackspace extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (str.length() > 1) {
                    str = str.substring(0, str.length() - 1);
                    contentView.setTextViewText(R.id.textpwd, str);
                } else if (str.length() <= 1) {
                    str = "";
                    contentView.setTextViewText(R.id.textpwd, str);
                }
            notificationBuilder.setCustomBigContentView(contentView);
            mNotificationManager.notify(1, notificationBuilder.build());
        }

    }

    public static class switchButtonListenersubmit extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(str.equals(sharedPreferences.getString("masterpass", "0000"))) {
                contentView.setTextViewText(R.id.title, "Your phone password is "+ sharedPreferences.getString("generatedpass", "0000"));
            }
            else {
                contentView.setTextViewText(R.id.title, "Wrong master password");
            }
            notificationBuilder.setCustomBigContentView(contentView);
            mNotificationManager.notify(1, notificationBuilder.build());
        }

    }
}