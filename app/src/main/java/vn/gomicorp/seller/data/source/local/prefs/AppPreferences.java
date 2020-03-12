package vn.gomicorp.seller.data.source.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private static final int PRIVATE_MODE = 0;
    private static final String PREFS_NAME = "EAPPS";


    public AppPreferences(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, PRIVATE_MODE);
        editor = prefs.edit();
    }


    /**
     * Device Token
     */
    private static final String DEVICE_TOKEN = "DEVICE_TOKEN";

    public void setDeviceToken(String token) {
        editor.putString(DEVICE_TOKEN, token);
        editor.commit();
    }

    public String getDeviceToken() {
        return prefs.getString(DEVICE_TOKEN, "");
    }
}
