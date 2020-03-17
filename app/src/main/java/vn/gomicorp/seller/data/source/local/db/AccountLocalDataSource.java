package vn.gomicorp.seller.data.source.local.db;

import vn.gomicorp.seller.data.AccountDataSource;
import vn.gomicorp.seller.data.ResultListener;
import vn.gomicorp.seller.data.source.model.api.ForgetPwdRequest;
import vn.gomicorp.seller.data.source.model.api.ResetPwdRequest;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.api.SignInRequest;
import vn.gomicorp.seller.data.source.model.api.SignUpRequest;
import vn.gomicorp.seller.data.source.model.data.Account;

public class AccountLocalDataSource implements AccountDataSource {
    @Override
    public void signin(SignInRequest request, ResultListener<ResponseData<Account>> callback) {

    }

    @Override
    public void signup(SignUpRequest request, ResultListener<ResponseData<Account>> callback) {

    }

    @Override
    public void forgetPwd(ForgetPwdRequest request, ResultListener<ResponseData<Account>> callback) {

    }

    @Override
    public void resetPwd(ResetPwdRequest request, ResultListener<ResponseData<Account>> callback) {

    }
}
