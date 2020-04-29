package vn.gomisellers.apps;

import android.app.Application;
import android.content.Context;

import vn.gomisellers.apps.data.source.local.prefs.AppPreferences;
import vn.gomisellers.apps.utils.LogUtils;

public class EappsApplication extends Application {
    private static EappsApplication instance;

    private static AppPreferences appPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (BuildConfig.DEBUG) {
            LogUtils.setLogLevel(LogUtils.LogLevel.LOG_LEVEL_VERBOSE);
        } else {
            LogUtils.setLogLevel(LogUtils.LogLevel.LOG_LEVEL_NONE);
        }
    }

    public static synchronized EappsApplication getInstance() {
        return instance;
    }

    public Context getAppContext() {
        return instance.getApplicationContext();
    }

    public static AppPreferences getPreferences() {
        if (appPreferences == null)
            appPreferences = new AppPreferences(instance.getAppContext());

        return appPreferences;
    }
}
