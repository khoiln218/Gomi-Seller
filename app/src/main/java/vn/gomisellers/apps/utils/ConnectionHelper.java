package vn.gomisellers.apps.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;

import vn.gomisellers.apps.EappsApplication;

/**
 * Created by KHOI LE on 4/6/2020.
 */
public class ConnectionHelper {

    private static ConnectionHelper INSTANCE;

    private ConnectivityManager cm;

    public static ConnectionHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConnectionHelper();
        }
        return INSTANCE;
    }

    private ConnectionHelper() {
        cm = (ConnectivityManager) EappsApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public void checkNetwork(OnCheckNetworkListener listener) {
        listener.onCheck(isOnline());
    }

    private boolean isOnline() {
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }

    public void registerNetworkCallback(ConnectivityManager.NetworkCallback callback) {
        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build();
        if (cm != null)
            cm.registerNetworkCallback(networkRequest, callback);
    }

    public void unregisterNetworkCallback(ConnectivityManager.NetworkCallback callback) {
        try {
            if (cm != null)
                cm.unregisterNetworkCallback(callback);
        } catch (Exception e) {
        }
    }

    public interface OnCheckNetworkListener {
        void onCheck(boolean isOnline);
    }
}
