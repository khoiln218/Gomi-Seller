package vn.gomisellers.apps.data.source.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import vn.gomisellers.apps.data.source.model.data.Account;
import vn.gomisellers.apps.data.source.model.data.Shop;

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

    /**
     * Account
     */
    private static final String SELLER_URL = "SELLER_URL";
    private static final String USER_ID = "USER_ID";
    private static final String USER_NAME = "USER_NAME";
    private static final String FULL_NAME = "FULL_NAME";
    private static final String AVATAR = "AVATAR";
    private static final String REFERRAL_CODE = "REFERRAL_CODE";
    private static final String SELLER_LEVEl = "SELLER_LEVEl";
    private static final String SHOP_ID = "SHOP_ID";
    private static final String SHOP_WEB_ADDRESS = "SHOP_WEB_ADDRESS";
    private static final String BALANCE = "BALANCE";

    public void setAccount(Account account) {
        editor.putString(SELLER_URL, account.getSellerUrl());
        editor.putString(USER_ID, account.getUserId());
        editor.putString(USER_NAME, account.getUserName());
        editor.putString(FULL_NAME, account.getFullName());
        editor.putString(AVATAR, account.getAvatar());
        editor.putString(REFERRAL_CODE, account.getReferralCode());
        editor.putInt(SELLER_LEVEl, account.getSellerLevel());
        editor.putString(SHOP_ID, account.getShopId());
        editor.commit();
    }

    public void setShop(Shop shop) {
        editor.putString(SHOP_ID, shop.getId());
        editor.putString(SHOP_WEB_ADDRESS, shop.getWebAddress());
        editor.putInt(BALANCE, (int) shop.getPointBalance());
        editor.commit();
    }

    public String getUserId() {
        return prefs.getString(USER_ID, null);
    }

    public String getUserName() {
        return prefs.getString(USER_NAME, null);
    }

    public String getFullName() {
        return prefs.getString(FULL_NAME, null);
    }

    public String getAvatar() {
        return prefs.getString(AVATAR, null);
    }

    public String getSellerUrl() {
        return prefs.getString(SELLER_URL, null);
    }

    public String getReferralCode() {
        return prefs.getString(REFERRAL_CODE, null);
    }

    public String getShopId() {
        return prefs.getString(SHOP_ID, null);
    }

    public String getWebAddress() {
        return prefs.getString(SHOP_WEB_ADDRESS, null);
    }

    public void clear() {
        String token = getDeviceToken();
        editor.clear();
        editor.putString(DEVICE_TOKEN, token);
        editor.commit();
    }

    public boolean isLogin() {
        return getUserId() != null;
    }
}
