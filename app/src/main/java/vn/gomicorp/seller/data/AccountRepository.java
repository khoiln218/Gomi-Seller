package vn.gomicorp.seller.data;

import vn.gomicorp.seller.data.source.model.api.ForgetPwdRequest;
import vn.gomicorp.seller.data.source.model.api.ResetPwdRequest;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.api.SignInRequest;
import vn.gomicorp.seller.data.source.model.api.SignUpRequest;
import vn.gomicorp.seller.data.source.model.api.VerifyPhoneNumberRequest;
import vn.gomicorp.seller.data.source.model.data.Account;
import vn.gomicorp.seller.data.source.remote.AccountRemoteDataSource;

import static androidx.core.util.Preconditions.checkNotNull;

public class AccountRepository implements AccountDataSource {
    private volatile static AccountRepository INSTANCE = null;
    private AccountDataSource mRemoteDataSource;

    private AccountRepository() {
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
}
