package vn.gomicorp.seller.utils;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import vn.gomicorp.seller.BuildConfig;

public final class Utils {

    public static OkHttpClient createHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(GomiConstants.HTTP_CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(GomiConstants.HTTP_READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(GomiConstants.HTTP_WRITE_TIME_OUT, TimeUnit.SECONDS);
        return builder.build();

    }

    public static OkHttpClient createHttpLoggingClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(GomiConstants.HTTP_CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(GomiConstants.HTTP_READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(GomiConstants.HTTP_WRITE_TIME_OUT, TimeUnit.SECONDS);

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
        return "12345678";
    }

    public static String getDeviceVersion() {
        return "27";
    }
}
