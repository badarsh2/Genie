package com.example.adarsh.lockscreen.Utilities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.example.adarsh.lockscreen.R;

/**
 * Created by srivatsan on 23/07/16.
 */

public class NotificationGenerator {

    static RemoteViews contentView;
    static NotificationCompat.Builder notificationBuilder;
    static NotificationManager mNotificationManager;
    static String str;
    static SharedPreferences sharedPreferences;

    public NotificationGenerator(Context context){
        int icon = R.drawable.notif;
        long when = System.currentTimeMillis();
        str = "";
        sharedPreferences = context.getSharedPreferences("MASTERDATA", Context.MODE_PRIVATE);
        mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        contentView = new RemoteViews(context.getPackageName(), R.layout.custom_notification);
        notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(icon)
                .setContent(contentView)
                .setContentTitle("Custom Notification")
                .setOngoing(false)
                .setWhen(when);
        notificationBuilder.setCustomBigContentView(contentView);

        Intent switchIntentzero = new Intent(context, SwitchButtonListenerZero.class);
        PendingIntent pendingSwitchIntentzero = PendingIntent.getBroadcast(context, 0,
                switchIntentzero, 0);
        contentView.setOnClickPendingIntent(R.id.btn0, pendingSwitchIntentzero);

        Intent switchIntentone = new Intent(context, SwitchButtonListenerOne.class);
        PendingIntent pendingSwitchIntentone = PendingIntent.getBroadcast(context, 0,
                switchIntentone, 0);
        contentView.setOnClickPendingIntent(R.id.btn1, pendingSwitchIntentone);

        Intent switchIntenttwo = new Intent(context, SwitchButtonListenerTwo.class);
        PendingIntent pendingSwitchIntenttwo = PendingIntent.getBroadcast(context, 0,
                switchIntenttwo, 0);
        contentView.setOnClickPendingIntent(R.id.btn2, pendingSwitchIntenttwo);

        Intent switchIntentthree = new Intent(context, SwitchButtonListenerThree.class);
        PendingIntent pendingSwitchIntentthree = PendingIntent.getBroadcast(context, 0,
                switchIntentthree, 0);
        contentView.setOnClickPendingIntent(R.id.btn3, pendingSwitchIntentthree);

        Intent switchIntentfour = new Intent(context, SwitchButtonListenerFour.class);
        PendingIntent pendingSwitchIntentfour = PendingIntent.getBroadcast(context, 0,
                switchIntentfour, 0);
        contentView.setOnClickPendingIntent(R.id.btn4, pendingSwitchIntentfour);

        Intent switchIntentfive = new Intent(context, SwitchButtonListenerFive.class);
        PendingIntent pendingSwitchIntentfive = PendingIntent.getBroadcast(context, 0,
                switchIntentfive, 0);
        contentView.setOnClickPendingIntent(R.id.btn5, pendingSwitchIntentfive);

        Intent switchIntentsix = new Intent(context, SwitchButtonListenerSix.class);
        PendingIntent pendingSwitchIntentsix = PendingIntent.getBroadcast(context, 0,
                switchIntentsix, 0);
        contentView.setOnClickPendingIntent(R.id.btn6, pendingSwitchIntentsix);

        Intent switchIntentseven = new Intent(context, SwitchButtonListenerSeven.class);
        PendingIntent pendingSwitchIntentseven = PendingIntent.getBroadcast(context, 0,
                switchIntentseven, 0);
        contentView.setOnClickPendingIntent(R.id.btn7, pendingSwitchIntentseven);

        Intent switchIntenteight = new Intent(context, SwitchButtonListenerEight.class);
        PendingIntent pendingSwitchIntenteight = PendingIntent.getBroadcast(context, 0,
                switchIntenteight, 0);
        contentView.setOnClickPendingIntent(R.id.btn8, pendingSwitchIntenteight);

        Intent switchIntentnine = new Intent(context, SwitchButtonListenerNine.class);
        PendingIntent pendingSwitchIntentnine = PendingIntent.getBroadcast(context, 0,
                switchIntentnine, 0);
        contentView.setOnClickPendingIntent(R.id.btn9, pendingSwitchIntentnine);

        Intent switchIntentbackspace = new Intent(context, SwitchButtonListenerBackspace.class);
        PendingIntent pendingSwitchIntentbackspace = PendingIntent.getBroadcast(context, 0,
                switchIntentbackspace, 0);
        contentView.setOnClickPendingIntent(R.id.backspace, pendingSwitchIntentbackspace);

        Intent switchIntentsubmit = new Intent(context, SwitchButtonListenerSubmit.class);
        PendingIntent pendingSwitchIntentsubmit = PendingIntent.getBroadcast(context, 0,
                switchIntentsubmit, 0);
        contentView.setOnClickPendingIntent(R.id.submit, pendingSwitchIntentsubmit);

        contentView.setTextViewText(R.id.textpwd, "");
    }

    public void addNotification() {
        mNotificationManager.notify(1, notificationBuilder.build());
    }

    public static class SwitchButtonListenerZero extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            str = str.concat("0");
            contentView.setTextViewText(R.id.textpwd, str);
            // notif_display();
            notificationBuilder.setCustomBigContentView(contentView);
            mNotificationManager.notify(1, notificationBuilder.build());
        }

    }

    public static class SwitchButtonListenerOne extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            str = str.concat("1");
            contentView.setTextViewText(R.id.textpwd, str);
            // notif_display();
            notificationBuilder.setCustomBigContentView(contentView);
            mNotificationManager.notify(1, notificationBuilder.build());
        }

    }

    public static class SwitchButtonListenerTwo extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            str = str.concat("2");
            contentView.setTextViewText(R.id.textpwd, str);
            // notif_display();
            notificationBuilder.setCustomBigContentView(contentView);
            mNotificationManager.notify(1, notificationBuilder.build());
        }

    }

    public static class SwitchButtonListenerThree extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            str = str.concat("3");
            contentView.setTextViewText(R.id.textpwd, str);
            // notif_display();
            notificationBuilder.setCustomBigContentView(contentView);
            mNotificationManager.notify(1, notificationBuilder.build());
        }

    }

    public static class SwitchButtonListenerFour extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            str = str.concat("4");
            contentView.setTextViewText(R.id.textpwd, str);
            // notif_display();
            notificationBuilder.setCustomBigContentView(contentView);
            mNotificationManager.notify(1, notificationBuilder.build());
        }

    }

    public static class SwitchButtonListenerFive extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            str = str.concat("5");
            contentView.setTextViewText(R.id.textpwd, str);
            // notif_display();
            notificationBuilder.setCustomBigContentView(contentView);
            mNotificationManager.notify(1, notificationBuilder.build());
        }

    }

    public static class SwitchButtonListenerSix extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            str = str.concat("6");
            contentView.setTextViewText(R.id.textpwd, str);
            // notif_display();
            notificationBuilder.setCustomBigContentView(contentView);
            mNotificationManager.notify(1, notificationBuilder.build());
        }

    }

    public static class SwitchButtonListenerSeven extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            str = str.concat("7");
            contentView.setTextViewText(R.id.textpwd, str);
            // notif_display();
            notificationBuilder.setCustomBigContentView(contentView);
            mNotificationManager.notify(1, notificationBuilder.build());
        }

    }

    public static class SwitchButtonListenerEight extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            str = str.concat("8");
            contentView.setTextViewText(R.id.textpwd, str);
            // notif_display();
            notificationBuilder.setCustomBigContentView(contentView);
            mNotificationManager.notify(1, notificationBuilder.build());
        }

    }

    public static class SwitchButtonListenerNine extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            str = str.concat("9");
            contentView.setTextViewText(R.id.textpwd, str);
            // notif_display();
            notificationBuilder.setCustomBigContentView(contentView);
            mNotificationManager.notify(1, notificationBuilder.build());
        }

    }

    public static class SwitchButtonListenerBackspace extends BroadcastReceiver {
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

    public static class SwitchButtonListenerSubmit extends BroadcastReceiver {
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
