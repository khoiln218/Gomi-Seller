package vn.gomicorp.seller.main.mypage.info;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.DatePicker;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.Calendar;
import java.util.TimeZone;

import vn.gomicorp.seller.BaseActivity;
import vn.gomicorp.seller.R;
import vn.gomicorp.seller.databinding.ActivityAccountInformationBinding;
import vn.gomicorp.seller.utils.Utils;

public class AccountInformationActivity extends BaseActivity<AccountInformationViewModel, ActivityAccountInformationBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initToolbar(getString(R.string.account_change_info));
        initCmd();

        getViewModel().requestAccountInformation();
    }

    private void initCmd() {
        getViewModel().getCmd().observe(this, new Observer<InfoEvent>() {
            @Override
            public void onChanged(InfoEvent event) {
                if (event.getCode() == InfoEvent.HIDE_KEYBOARD) {
                    Utils.hideSoftKeyboard(AccountInformationActivity.this);
                } else if (event.getCode() == InfoEvent.SHOW_DATE_PICKER) {
                    showDatePickerDialog((long) event.getData());
                }
            }
        });
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_account_information);
        viewModel = ViewModelProviders.of(this).get(AccountInformationViewModel.class);
        getBinding().setViewModel(getViewModel());
        binding.setLifecycleOwner(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showDatePickerDialog(long time) {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.clear();
                calendar.setTimeZone(TimeZone.getTimeZone("GMT-0:00"));
                calendar.set(year, monthOfYear, dayOfMonth);
                getViewModel().setBirthday(calendar);
            }
        };

        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT-0:00"));
        calendar.setTimeInMillis(time);
        DatePickerDialog pic = new DatePickerDialog(AccountInformationActivity.this, callback, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        pic.setTitle(getString(R.string.select_birthday_title));
        pic.show();
    }
}
