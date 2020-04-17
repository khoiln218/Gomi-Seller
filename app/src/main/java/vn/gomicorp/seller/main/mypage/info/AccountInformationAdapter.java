package vn.gomicorp.seller.main.mypage.info;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import vn.gomicorp.seller.main.mypage.info.basic.AccountInformationFragment;
import vn.gomicorp.seller.main.mypage.info.password.ChangePasswordFragment;

/**
 * Created by KHOI LE on 4/17/2020.
 */
public class AccountInformationAdapter extends FragmentStateAdapter {
    public static final int COUNT = 2;
    public static final int INFO = 0;

    private AccountInformationFragment accountInformationFragment;
    private ChangePasswordFragment changePasswordFragment;

    public AccountInformationAdapter(@NonNull AccountInformationActivity activity) {
        super(activity);
        accountInformationFragment = AccountInformationFragment.newInstant(activity);
        changePasswordFragment = ChangePasswordFragment.newInstant(activity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == INFO)
            return accountInformationFragment;
        else
            return changePasswordFragment;
    }

    @Override
    public int getItemCount() {
        return COUNT;
    }
}
