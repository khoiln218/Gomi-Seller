package vn.gomisellers.apps.widgets.dialog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;

import vn.gomisellers.apps.R;
import vn.gomisellers.apps.data.source.model.data.BankAccount;
import vn.gomisellers.apps.utils.GomiConstants;
import vn.gomisellers.apps.utils.Strings;
import vn.gomisellers.apps.widgets.NumericKeypad;

public class WithdrawnBankDialogFragment extends BottomSheetDialogFragment implements NumericKeypad.OnActionListener, View.OnClickListener {

    private static final String TAG = WithdrawnBankDialogFragment.class.getSimpleName();


    private View inputLayout, acceptLayout;
    private TextInputLayout inputLayoutAmount;
    private EditText inputAmount;
    private ImageView imageBank;
    private TextView textBankName, textAccountNumber, textAccountHolder,
            textAmount, textFee, textReceive;

    private BankAccount bankAccount;
    private String strAmount;

    public static WithdrawnBankDialogFragment newInstance(BankAccount bankAccount) {
        Bundle args = new Bundle();
        args.putParcelable(GomiConstants.EXTRA_PARCELABLE, bankAccount);

        WithdrawnBankDialogFragment fragment = new WithdrawnBankDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bankAccount = getArguments().getParcelable(GomiConstants.EXTRA_PARCELABLE);
        strAmount = "";
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_withdrawn_bank_dialog, container, false);

        inputLayout = view.findViewById(R.id.input_layout);
        acceptLayout = view.findViewById(R.id.accept_layout);

        inputLayout.setVisibility(View.VISIBLE);
        acceptLayout.setVisibility(View.GONE);

        inputLayoutAmount = view.findViewById(R.id.input_layout_amount);
        inputAmount = view.findViewById(R.id.input_amount);
        inputAmount.addTextChangedListener(new InputWatcher());

        NumericKeypad numericKeypad = view.findViewById(R.id.numeric_keypad);
        numericKeypad.setOnActionListener(this);

        imageBank = view.findViewById(R.id.image_bank);
        textBankName = view.findViewById(R.id.text_bank_name);
        textAccountNumber = view.findViewById(R.id.text_account_number);
        textAccountHolder = view.findViewById(R.id.text_account_holder);

        textAmount = view.findViewById(R.id.text_amount);
        textFee = view.findViewById(R.id.text_fee);
        textReceive = view.findViewById(R.id.text_receive);

        view.findViewById(R.id.btn_close).setOnClickListener(this);
        view.findViewById(R.id.btn_continue).setOnClickListener(this);
        view.findViewById(R.id.btn_back).setOnClickListener(this);
        view.findViewById(R.id.btn_continue).setOnClickListener(this);
        view.findViewById(R.id.btn_accept).setOnClickListener(this);

        setBankAccountElements();
        setAmount();

        return view;
    }

    @Override
    public void onAction(String s) {
        if (Strings.isNullOrEmpty(s)) {
            if (strAmount.length() > 0)
                strAmount = strAmount.substring(0, strAmount.length() - 1);
        } else
            strAmount += s;

        setAmount();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close:
                dismiss();
                break;

            case R.id.btn_continue:
                if (validateAmount())
                    showAcceptLayout();
                break;

            case R.id.btn_back:
                showInputLayout();
                break;
        }
    }


    /**
     *
     */
    private void setBankAccountElements() {

//        Glide.with(getActivity())
//                .load(bankAccount.getBankAvatar())
//                .apply(new RequestOptions()
//                        .placeholder(R.drawable.ic_place_holder)
//                        .fitCenter()
//                        .diskCacheStrategy(DiskCacheStrategy.NONE)
//                        .skipMemoryCache(true))
//                .into(imageBank);
//
//        textBankName.setText(bankAccount.getBankName());
//        textAccountNumber.setText(Strings.toAccountNumberFormat(bankAccount.getAccountNumber()));
//        textAccountHolder.setText(bankAccount.getAccountHolder());
    }

    private void setAmount() {
//        if (Strings.toDouble(strAmount) == 0)
//            strAmount = "";
//
//        double amount = Strings.toDouble(strAmount);
//        double fee = 0;
//
//        inputAmount.setText(Numbers.currencyFormat(amount));
//
//        textAmount.setText(Numbers.currencyFormat(amount));
//        textFee.setText(Numbers.currencyFormat(fee));
//        textReceive.setText(Numbers.currencyFormat(amount - fee));
    }

    private boolean validateAmount() {
//        int amount = (int) Strings.toDouble(strAmount);
//        String msg = "";
//
//        if (amount == 0)
//            msg = getString(R.string.err_empty_amount);
//
//        else if (amount < LiveConstants.MINIMUM_AMOUNT)
//            msg = getString(R.string.err_minimum_amount).replace("{amount}", Numbers.currencyFormat(LiveConstants.MINIMUM_AMOUNT));
//
//        else if (amount > LiveConstants.MAXIMUM_AMOUNT)
//            msg = getString(R.string.err_maximum_amount).replace("{amount}", Numbers.currencyFormat(LiveConstants.MAXIMUM_AMOUNT));
//
//        else if (amount > AppController.getPreferences().getBalance())
//            msg = getString(R.string.err_not_enough);
//
//        if (!Strings.isNullOrEmpty(msg)) {
//            inputLayoutAmount.setError(msg);
//            Utils.requestFocus(getActivity(), inputAmount);
//            Utils.playVibrate(getActivity());
//
//            return false;
//        }
//
//        inputLayoutAmount.setErrorEnabled(false);
        return true;
    }

    private void showAcceptLayout() {
        if (inputLayout.getVisibility() == View.GONE)
            return;

        inputLayout.animate()
                .alpha(0.0f)
                .translationY(inputLayout.getHeight())
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        acceptLayout.setVisibility(View.VISIBLE);
                        inputLayout.setVisibility(View.GONE);
                    }
                });
    }

    private void showInputLayout() {
        if (acceptLayout.getVisibility() == View.GONE)
            return;

        inputLayout.animate()
                .alpha(1.0f)
                .translationY(0.0f)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        acceptLayout.setVisibility(View.GONE);
                        inputLayout.setVisibility(View.VISIBLE);
                    }
                });
    }


    /**
     *
     */
    private class InputWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            inputLayoutAmount.setErrorEnabled(false);
        }
    }
}
