package vn.gomisellers.apps.data;

import vn.gomisellers.apps.data.source.model.api.AccountChangePasswordRequest;
import vn.gomisellers.apps.data.source.model.api.AccountRequest;
import vn.gomisellers.apps.data.source.model.api.AccountUpdateRequest;
import vn.gomisellers.apps.data.source.model.api.ChangeAvatarRequest;
import vn.gomisellers.apps.data.source.model.api.ForgetPwdRequest;
import vn.gomisellers.apps.data.source.model.api.ResetPwdRequest;
import vn.gomisellers.apps.data.source.model.api.ResponseData;
import vn.gomisellers.apps.data.source.model.api.SignInRequest;
import vn.gomisellers.apps.data.source.model.api.SignOutRequest;
import vn.gomisellers.apps.data.source.model.api.SignUpRequest;
import vn.gomisellers.apps.data.source.model.api.VerifyPhoneNumberRequest;
import vn.gomisellers.apps.data.source.model.data.Account;
import vn.gomisellers.apps.data.source.remote.AccountRemoteDataSource;
import vn.gomisellers.apps.data.source.test.MockAccoutDataSource;
import vn.gomisellers.apps.utils.GomiConstants;

import static androidx.core.util.Preconditions.checkNotNull;

public class AccountRepository implements AccountDataSource {
    private volatile static AccountRepository INSTANCE = null;
    private AccountDataSource mRemoteDataSource;

    private AccountRepository() {
        if (GomiConstants.TEST)
            mRemoteDataSource = new MockAccoutDataSource();
        else
            mRemoteDataSource = new AccountRemoteDataSource();
    }

    public static AccountRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (AccountRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AccountRepository();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void signin(SignInRequest request, ResultListener<ResponseData<Account>> callback) {
        checkNotNull(callback);

        mRemoteDataSource.signin(request, callback);
    }

    @Override
    public void signup(SignUpRequest request, ResultListener<ResponseData<Account>> callback) {
        checkNotNull(callback);

        mRemoteDataSource.signup(request, callback);
    }

    @Override
    public void forgetPwd(ForgetPwdRequest request, ResultListener<ResponseData<Account>> callback) {
        checkNotNull(callback);

        mRemoteDataSource.forgetPwd(request, callback);
    }

    @Override
    public void resetPwd(ResetPwdRequest request, ResultListener<ResponseData<Account>> callback) {
        checkNotNull(callback);
        mRemoteDataSource.resetPwd(request, callback);
    }

    @Override
    public void verifyPhoneNumber(VerifyPhoneNumberRequest request, ResultListener<ResponseData<Account>> callback) {
        checkNotNull(callback);
        mRemoteDataSource.verifyPhoneNumber(request, callback);
    }

    @Override
    public void findbyid(AccountRequest request, ResultListener<ResponseData<Account>> callback) {
        mRemoteDataSource.findbyid(request, callback);
    }

    @Override
    public void updateinfo(AccountUpdateRequest request, ResultListener<ResponseData<Account>> callback) {
        mRemoteDataSource.updateinfo(request, callback);
    }

    @Override
    public void changepassword(AccountChangePasswordRequest request, ResultListener<ResponseData<Account>> callback) {
        mRemoteDataSource.changepassword(request, callback);
    }

    @Override
    public void changeavatar(ChangeAvatarRequest request, ResultListener<ResponseData<Account>> callback) {
        mRemoteDataSource.changeavatar(request, callback);
    }

    @Override
    public void logout(SignOutRequest request, ResultListener<ResponseData<Account>> callback) {
        mRemoteDataSource.logout(request, callback);
    }
}
