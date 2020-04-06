package vn.gomicorp.seller.utils;

import android.app.Activity;
import android.content.Intent;

import vn.gomicorp.seller.EappsApplication;
import vn.gomicorp.seller.main.MainActivity;
import vn.gomicorp.seller.main.market.collection.CollectionActivity;
import vn.gomicorp.seller.main.market.collection.subcate.SubCategoryActivity;
import vn.gomicorp.seller.shopinfo.ShopInformationActivity;

/**
 * Created by KHOI LE on 3/19/2020.
 */
public class Intents {
    public static void directToMainActivity(Activity activity) {
        if (Strings.isNullOrEmpty(EappsApplication.getPreferences().getShopId()))
            startNewTaskActivity(activity, ShopInformationActivity.class);
        else
            startMainActivity(activity);
    }

    public static void startMainActivity(Activity activity) {
        startNewTaskActivity(activity, MainActivity.class);
    }

    public static void startNewTaskActivity(Activity activity, Class cls) {
        Intent intent = new Intent(activity, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void startCollectionActivity(Activity activity, int type, int id, String title) {
        Intent intent = new Intent(activity, CollectionActivity.class);
        intent.putExtra(GomiConstants.EXTRA_TYPE, type);
        intent.putExtra(GomiConstants.EXTRA_ID, id);
        intent.putExtra(GomiConstants.EXTRA_TITLE, title);

        activity.startActivity(intent);
    }

    public static void startCategoryActivity(Activity activity, int type, int id, String title) {
        Intent intent = new Intent(activity, SubCategoryActivity.class);
        intent.putExtra(GomiConstants.EXTRA_TYPE, type);
        intent.putExtra(GomiConstants.EXTRA_ID, id);
        intent.putExtra(GomiConstants.EXTRA_TITLE, title);

        activity.startActivity(intent);
    }
}
