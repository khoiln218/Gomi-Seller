package vn.gomicorp.seller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

/**
 * Created by KHOI LE on 4/9/2020.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected ViewDataBinding binding;
    protected ViewModel viewModel;

    protected abstract void initBinding();

    protected void initToolbar(String name) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(name);
        }
    }
}
