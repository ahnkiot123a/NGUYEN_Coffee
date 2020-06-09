package com.koit.project_prm391_1;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.google.gson.Gson;

public class App extends Application {

    public static  final String NOTIFICATION_CHANEL = "NOTIFICATION_CHANEL";
    private static App mSelf;
    private Gson mGSon;

    public static App self() {
        return mSelf;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSelf = this;
        mGSon = new Gson();
        createNotification();

    }

    private void createNotification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANEL, "notification", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("This is ....");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }


    }

    public Gson getGSon() {
        return mGSon;
    }
}
