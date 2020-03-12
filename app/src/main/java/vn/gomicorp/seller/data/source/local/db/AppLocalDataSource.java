package vn.gomicorp.seller.data.source.local.db;

import vn.gomicorp.seller.data.AppDataSource;
import vn.gomicorp.seller.data.ResultListener;
import vn.gomicorp.seller.data.source.model.api.SignInRequest;
import vn.gomicorp.seller.data.source.model.api.SignUpRequest;
import vn.gomicorp.seller.data.source.model.data.AccountInfo;

public class AppLocalDataSource implements AppDataSource {
    @Override
    public void signin(SignInRequest request, ResultListener<AccountInfo> callback) {

    }

    @Override
    public void signup(SignUpRequest request, ResultListener<AccountInfo> callback) {

    }
}
