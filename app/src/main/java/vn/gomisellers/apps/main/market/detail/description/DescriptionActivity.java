package vn.gomisellers.apps.main.market.detail.description;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

import vn.gomisellers.apps.EappsApplication;
import vn.gomisellers.apps.R;
import vn.gomisellers.apps.utils.GomiConstants;
import vn.gomisellers.apps.utils.Utils;

public class DescriptionActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        if (getIntent() == null) finish();
        String description = getIntent().getStringExtra(GomiConstants.EXTRA_DATA);

        progressBar = findViewById(R.id.progress_bar);
        webView = findViewById(R.id.web_view);

        initToolbar(getString(R.string.product_description));
        initWeb();
        loadWeb(description);
    }

    private void initToolbar(String name) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(name);
        }
    }

    private void loadWeb(String description) {
        String data = EappsApplication.getInstance().getString(R.string.html).replace("{body_content}", description)
                .replace("{width}", String.valueOf(Utils.getScreenWidth()));
        webView.loadData(data, "text/html", "UTF-8");
    }

    private void initWeb() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
