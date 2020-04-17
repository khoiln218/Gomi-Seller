package vn.gomicorp.seller.main.mypage.info.basic;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.Calendar;
import java.util.TimeZone;

import vn.gomicorp.seller.BaseFragment;
import vn.gomicorp.seller.R;
import vn.gomicorp.seller.databinding.AccountInformationFragmentBinding;
import vn.gomicorp.seller.main.mypage.info.AccountInfoListener;
import vn.gomicorp.seller.utils.Utils;

public class AccountInformationFragment extends BaseFragment<AccountInfoViewModel, AccountInformationFragmentBinding> {

    private AccountInfoListener listener;

    public static AccountInformationFragment newInstant(AccountInfoListener listener) {
        return new AccountInformationFragment(listener);
    }

    private AccountInformationFragment(AccountInfoListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_information_fragment, container, false);
        if (binding == null)
            binding = AccountInformationFragmentBinding.bind(view);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(AccountInfoViewModel.class);
        getBinding().setViewModel(getViewModel());
        getBinding().setLifecycleOwner(this);
        initCmd();
        getViewModel().requestAccountInformation();
    }

    private void initCmd() {
        getViewModel().getCmd().observe(this, new Observer<ChangeInfoEvent>() {
            @Override
            public void onChanged(ChangeInfoEvent event) {
                switch (event.getCode()) {
                    case ChangeInfoEvent.HIDE_KEYBOARD:
                        if (getActivity() != null)
                            Utils.hideSoftKeyboard(getActivity());
                        break;
                    case ChangeInfoEvent.SHOW_DATE_PICKER:
                        showDatePickerDialog(event.getData());
                        break;
                    case ChangeInfoEvent.SHOW_LOADING:
                        if (listener != null)
                            listener.showLoading();
                        break;
                    case ChangeInfoEvent.HIDE_LOADING:
                        if (listener != null)
                            listener.hideLoading();
                        break;
                    case ChangeInfoEvent.UPDATE_DONE:
                        if (listener != null)
                            listener.done();
                        break;
                }
            }
        });
    }

    private void showDatePickerDialog(long time) {
        if (getActivity() == null) return;
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
        DatePickerDialog pic = new DatePickerDialog(getActivity(), callback, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        pic.setTitle(getString(R.string.select_birthday_title));
        pic.show();
    }
}
