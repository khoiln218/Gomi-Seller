package vn.gomisellers.apps;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import vn.gomisellers.apps.utils.Intents;
import vn.gomisellers.apps.utils.LogUtils;

public class SplashActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDeviceToken();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (EappsApplication.getPreferences().isLogin()) {
                    Intents.directToMainActivity(SplashActivity.this);
                    return;
                }
                startSigIn();
            }
        }, 1500);
    }

    private void startSigIn() {
        Intents.startLoginActivity(this);
    }

    private void getDeviceToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            LogUtils.writeException(task.getException());
                            return;
                        }
                        if (task.getResult() != null) {
                            String token = task.getResult().getToken();
                            LogUtils.d(TAG, "DeviceToken: " + token);
                            EappsApplication.getPreferences().setDeviceToken(token);
                        }
                    }
                });
    }
}
