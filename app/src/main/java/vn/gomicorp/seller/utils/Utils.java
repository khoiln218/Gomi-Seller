package vn.gomicorp.seller.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Vibrator;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import vn.gomicorp.seller.EappsApplication;

public final class Utils {

    public static boolean isEmpty(TextView editText, String message) {
        if (TextUtils.isEmpty(editText.getText().toString().trim())) {
            editText.setError(message);
            return true;
        }
        return false;
    }

    public static boolean isEmailValid(TextView editText, String error) {
        String email = editText.getText().toString().trim();
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editText.setError(error);
            return false;
        }
        return true;
    }

    public static boolean isAccountValid(TextView editText, String error) {
        String account = editText.getText().toString().trim();
        if (TextUtils.isEmpty(account)
                || (!Patterns.PHONE.matcher(account).matches() && !Patterns.EMAIL_ADDRESS.matcher(account).matches())) {
            editText.setError(error);
            return false;
        }
        return true;
    }

    public static String getDeviceToken() {
        return EappsApplication.getPreferences().getDeviceToken();
    }

    public static String getDeviceVersion() {
        return String.format("Android %s, API %d", Build.VERSION.RELEASE, Build.VERSION.SDK_INT);
    }

    /**
     * Set Focus View
     *
     * @param context
     * @param view
     */
    public static void requestFocus(Context context, View view) {
        if (view.requestFocus())
            ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    /**
     * Play Vibrate
     *
     * @param context
     */
    public static void playVibrate(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        assert vibrator != null;
        vibrator.vibrate(500);
    }

    /**
     * Get Display Width
     *
     * @return
     */
    public static int getScreenWidth() {
        Context context = EappsApplication.getInstance().getApplicationContext();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        assert windowManager != null;
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        return displayMetrics.widthPixels;
    }

    /**
     * Get Resources Color
     *
     * @param colorId
     * @return
     */
    public static int getColorResources(int colorId) {
        Context context = EappsApplication.getInstance().getAppContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return context.getResources().getColor(colorId, context.getTheme());
        else
            return context.getResources().getColor(colorId);
    }

    /**
     * Set Html Tag to Text View
     *
     * @param source
     * @return
     */
    public static Spanned fromHtml(String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        else
            return Html.fromHtml(source);
    }
}
