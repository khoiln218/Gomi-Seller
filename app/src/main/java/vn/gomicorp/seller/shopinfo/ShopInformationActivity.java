package vn.gomicorp.seller.shopinfo;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import vn.gomicorp.seller.R;
import vn.gomicorp.seller.utils.Intents;

public class ShopInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_infomation);
        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: direct MainActivity
                Intents.startMainActivity(ShopInformationActivity.this);
            }
        });
    }
}
