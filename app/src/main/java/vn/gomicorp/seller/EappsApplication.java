package vn.gomicorp.seller;

import android.app.Application;
import android.content.Context;

import vn.gomicorp.seller.utils.PreferencesHelper;

public class EappsApplication extends Application {
    private static EappsApplication instance;

    private static PreferencesHelper preferencesHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static synchronized EappsApplication getInstance() {
        return instance;
    }

    public Context getAppContext() {
        return instance.getApplicationContext();
    }

    public static PreferencesHelper getPreferences() {
        if (preferencesHelper == null)
            preferencesHelper = new PreferencesHelper(instance.getAppContext());

        return preferencesHelper;
    }
}
