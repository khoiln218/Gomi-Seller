package vn.gomicorp.seller.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import vn.gomicorp.seller.EappsApplication;

/**
 * Created by KHOI LE on 4/6/2020.
 */
public class ConnectionHelper {
    public static void checkNetwork(OnCheckNetworkListener listener) {
        listener.onCheck(isOnline());
    }

    static boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) EappsApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public interface OnCheckNetworkListener {
        void onCheck(boolean isOnline);
    }
}
