package vn.gomicorp.seller.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import vn.gomicorp.seller.BuildConfig;
import vn.gomicorp.seller.EappsApplication;

public final class Utils {
    public static int HTTP_CONNECTION_TIME_OUT = 10; // 10 seconds
    public static int HTTP_READ_TIME_OUT = 60; // 60 seconds, 0 -> no time out
    public static int HTTP_WRITE_TIME_OUT = 60; // 60 seconds, 0 - > no time mout

    public static OkHttpClient createHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(HTTP_CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(HTTP_READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(HTTP_WRITE_TIME_OUT, TimeUnit.SECONDS);
        return builder.build();

    }

    public static OkHttpClient createHttpLoggingClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(HTTP_CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(HTTP_READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(HTTP_WRITE_TIME_OUT, TimeUnit.SECONDS);

        builder.addInterceptor(logging);
        return builder.build();
    }

    public static Retrofit createRetrofit(final String baseUrl) {
        if (BuildConfig.DEBUG) {
            return createLoggingRetrofit(baseUrl);
        }

        return new Retrofit.Builder()
                .client(Utils.createHttpClient())
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public static final Retrofit createLoggingRetrofit(final String baseUrl) {
        return new Retrofit.Builder()
                .client(Utils.createHttpLoggingClient())
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

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
        vibrator.vibrate(500);
    }
}
