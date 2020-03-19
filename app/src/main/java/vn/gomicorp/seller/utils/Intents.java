package vn.gomicorp.seller.utils;

import android.app.Activity;
import android.content.Intent;

import vn.gomicorp.seller.EappsApplication;
import vn.gomicorp.seller.createshop.CreateShopActivity;
import vn.gomicorp.seller.main.MainActivity;

/**
 * Created by KHOI LE on 3/19/2020.
 */
public class Intents {
    public static void startMainActivity(Activity activity) {

        Intent intent;

        if (Strings.isNullOrEmpty(EappsApplication.getPreferences().getShopId()))
            intent = new Intent(activity, CreateShopActivity.class);
        else
            intent = new Intent(activity, MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);
        activity.finish();
    }
}
