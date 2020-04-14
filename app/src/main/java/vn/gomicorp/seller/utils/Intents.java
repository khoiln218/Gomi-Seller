package vn.gomicorp.seller.utils;

import android.app.Activity;
import android.content.Intent;

import vn.gomicorp.seller.EappsApplication;
import vn.gomicorp.seller.authen.signin.SignInActivity;
import vn.gomicorp.seller.main.MainActivity;
import vn.gomicorp.seller.main.market.collection.CollectionActivity;
import vn.gomicorp.seller.main.market.collection.cate.CategoryActivity;
import vn.gomicorp.seller.main.market.collection.subcate.SubCategoryActivity;
import vn.gomicorp.seller.main.market.detail.ProductDetailActivity;
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

    public static void startLoginActivity(Activity activity) {
        startNewTaskActivity(activity, SignInActivity.class);
    }

    private static void startNewTaskActivity(Activity activity, Class cls) {
        Intent intent = new Intent(activity, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void startCollectionActivity(Activity activity, int id, String title) {
        Intent intent = new Intent(activity, CollectionActivity.class);
        intent.putExtra(GomiConstants.EXTRA_ID, id);
        intent.putExtra(GomiConstants.EXTRA_TITLE, title);

        activity.startActivity(intent);
    }

    public static void startCategoryActivity(Activity activity, int id, String title) {
        Intent intent = new Intent(activity, CategoryActivity.class);
        intent.putExtra(GomiConstants.EXTRA_ID, id);
        intent.putExtra(GomiConstants.EXTRA_TITLE, title);

        activity.startActivity(intent);
    }

    public static void startSubCategoryActivity(Activity activity, int type, int id, String title) {
        Intent intent = new Intent(activity, SubCategoryActivity.class);
        intent.putExtra(GomiConstants.EXTRA_TYPE, type);
        intent.putExtra(GomiConstants.EXTRA_ID, id);
        intent.putExtra(GomiConstants.EXTRA_TITLE, title);

        activity.startActivity(intent);
    }

    public static void startProductDetailActivity(Activity activity, String productId) {
        Intent intent = new Intent(activity, ProductDetailActivity.class);
        intent.putExtra(GomiConstants.EXTRA_ID, productId);

        activity.startActivity(intent);
    }

    public static void startActionSend(Activity activity, String title, String subject, String text) {
        Intent target = new Intent(Intent.ACTION_SEND);
        target.setType("text/plain");

        target.putExtra(Intent.EXTRA_SUBJECT, subject);
        target.putExtra(Intent.EXTRA_TEXT, text);

        activity.startActivity(Intent.createChooser(target, title));
    }
}
