package vn.gomisellers.apps.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import vn.gomisellers.apps.R;

public class NumericKeypad extends LinearLayout implements View.OnClickListener {

    private OnActionListener actionListener;
    private View btn00, btn000;

    public void setOnActionListener(OnActionListener actionListener) {
        this.actionListener = actionListener;
    }

    public void invisibleButton() {
        btn00.setVisibility(INVISIBLE);
        btn000.setVisibility(INVISIBLE);
    }

    public NumericKeypad(Context context) {
        super(context);
        initLayout(context);
    }

    public NumericKeypad(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
    }

    public NumericKeypad(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);
    }

    private void initLayout(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_numeric_keypad, this, true);

        btn00 = view.findViewById(R.id.btn_00);
        btn000 = view.findViewById(R.id.btn_000);

        view.findViewById(R.id.btn_1).setOnClickListener(this);
        view.findViewById(R.id.btn_2).setOnClickListener(this);
        view.findViewById(R.id.btn_3).setOnClickListener(this);
        view.findViewById(R.id.btn_4).setOnClickListener(this);
        view.findViewById(R.id.btn_5).setOnClickListener(this);
        view.findViewById(R.id.btn_6).setOnClickListener(this);
        view.findViewById(R.id.btn_7).setOnClickListener(this);
        view.findViewById(R.id.btn_8).setOnClickListener(this);
        view.findViewById(R.id.btn_9).setOnClickListener(this);
        view.findViewById(R.id.btn_0).setOnClickListener(this);
        btn00.setOnClickListener(this);
        btn000.setOnClickListener(this);

        view.findViewById(R.id.btn_backspace).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_backspace:
                if (actionListener != null)
                    actionListener.onAction(null);
                break;

            default:
                if (actionListener != null)
                    actionListener.onAction(((Button) v).getText().toString());

                break;
        }
    }

    public interface OnActionListener {
        void onAction(String s);
    }
}
