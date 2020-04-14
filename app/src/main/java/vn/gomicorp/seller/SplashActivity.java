package vn.gomicorp.seller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import vn.gomicorp.seller.authen.signin.SignInActivity;
import vn.gomicorp.seller.authen.signup.SignUpActivity;

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
//        startSigUp();
        startSigIn();
    }

    private void startSigIn() {
        startActivity(new Intent(this, SignInActivity.class));
    }

    private void startSigUp() {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    private void getDeviceToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.d(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        String token = task.getResult().getToken();
                        EappsApplication.getPreferences().setDeviceToken(token);
                    }
                });
    }
}
