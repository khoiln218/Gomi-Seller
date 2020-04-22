package vn.gomicorp.seller.main;

import android.net.Uri;

/**
 * Created by KHOI LE on 4/17/2020.
 */
public interface MainListener {
    void requestPermission();

    void cropImage(Uri uri);
}
