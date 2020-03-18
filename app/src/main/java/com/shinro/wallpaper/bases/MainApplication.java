package com.shinro.wallpaper.bases;

import androidx.multidex.MultiDexApplication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shinro.wallpaper.api.LenientTypeAdapterFactory;
import com.shinro.wallpaper.ultis.AppLogger;

public class MainApplication extends MultiDexApplication {

    private Gson mGson;

    private static MainApplication mSelf;

    public static MainApplication self() {
        return mSelf;
    }

    public Gson getGson() {
        return mGson;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSelf = this;
        mGson = new GsonBuilder().registerTypeAdapterFactory(new LenientTypeAdapterFactory()).create();
        AppLogger.init();
    }

}
