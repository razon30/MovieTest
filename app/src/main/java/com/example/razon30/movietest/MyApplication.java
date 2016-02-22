package com.example.razon30.movietest;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.beardedhen.androidbootstrap.TypefaceProvider;

/**
 * Created by razon30 on 02-02-16.
 */
public class MyApplication extends Application {

    public static SharedPreferences preferences;
    public static Context context;
    private static  MyApplication sInstance;

    public static  MyApplication getInstance(){

        return sInstance;

    }

    public static Context getAppContext(){

        return sInstance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceProvider.registerDefaultIconSets();
        sInstance = this;
        context = this;
        preferences = getSharedPreferences(getPackageName() + "_preferences", MODE_PRIVATE);
    }


}
